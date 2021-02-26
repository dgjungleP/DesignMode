package com.dgj.project.ratelimiter.alg;

import com.dgj.project.ratelimiter.exception.InternalErrorException;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version: v1.0
 * @date: 2021/2/25
 * @author: dgj
 * 实现限流框架的限流算法
 */
public class FixedTimeWinRateLimitAlg implements RateLimitAlg {
    /**
     * 超时时间 {@code Lock.tryLock()}
     *
     * @see Lock
     */
    private static final long TRY_LOCK_TIMEOUT = 200L;
    private Stopwatch stopwatch;
    private AtomicInteger currentCount = new AtomicInteger(0);
    private final int limit;
    /**
     * 这里使用了重入锁
     */
    private Lock lock = new ReentrantLock();

    public FixedTimeWinRateLimitAlg(int limit) {
        this(Stopwatch.createStarted(), limit);
    }

    @VisibleForTesting
    protected FixedTimeWinRateLimitAlg(Stopwatch stopwatch, int limit) {
        this.stopwatch = stopwatch;
        this.limit = limit;
    }

    public boolean tryAcquire() throws InternalErrorException {
        int updatedCount = currentCount.incrementAndGet();
        if (updatedCount <= limit) {
            return true;
        }
        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMillis(1)) {
                        currentCount.set(0);
                        stopwatch.reset();
                    }
                    updatedCount = currentCount.incrementAndGet();
                    return updatedCount <= limit;
                } finally {
                    /*
                      这里的异常处理主要是为了解锁
                     */
                    lock.unlock();
                }
            } else {
                throw new InternalErrorException("tryAcquire() wait lock too long:" + TRY_LOCK_TIMEOUT + "ms");
            }
        } catch (InterruptedException e) {
            throw new InternalErrorException("tryAcquire() is interrupted by lock-time-out.", e);
        }
    }
}
