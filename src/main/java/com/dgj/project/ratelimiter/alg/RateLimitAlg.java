package com.dgj.project.ratelimiter.alg;

import com.dgj.project.ratelimiter.exception.InternalErrorException;

public interface RateLimitAlg {
    boolean tryAcquire() throws InternalErrorException;
}
