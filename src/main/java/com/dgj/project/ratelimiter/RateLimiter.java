package com.dgj.project.ratelimiter;

import com.dgj.project.ratelimiter.alg.FixedTimeWinRateLimitAlg;
import com.dgj.project.ratelimiter.exception.InternalErrorException;
import com.dgj.project.ratelimiter.rule.ApiLimit;
import com.dgj.project.ratelimiter.rule.RateLimitRule;
import com.dgj.project.ratelimiter.rule.RuleConfig;
import com.dgj.project.ratelimiter.rule.TrieRateLimitRule;
import com.dgj.project.ratelimiter.rule.datasource.FileRuleConfigSource;
import com.dgj.project.ratelimiter.rule.datasource.RuleConfigSource;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @version: v1.0
 * @date: 2021/2/25
 * @author: dgj
 * 整个系统的上帝类，用于串联起整个限流流程
 * 步骤：
 * 1.读取限流配置文件
 * 2.将限流规则映射到系统内存中
 * 3.构建一个支持快速查询的数据结构
 * 4.提供给用户一个直接使用的 {@link RateLimiter#limit(String, String)} 方法
 * @see RuleConfig
 * @see TrieRateLimitRule
 * @see RuleConfig.AppRuleConfig
 */
public class RateLimiter {
    private static final Logger log = LoggerFactory.getLogger(RateLimiter.class);
    /**
     * 为每个api在内存中存储限流计算器
     */
    private ConcurrentHashMap<String, FixedTimeWinRateLimitAlg> counters = new ConcurrentHashMap<>();
    private RateLimitRule rule;


    public RateLimiter() {
        RuleConfigSource source = new FileRuleConfigSource();
        RuleConfig sourceConfig = source.load();
        this.rule = new TrieRateLimitRule(sourceConfig);
    }

    public boolean limit(String appId, String url) throws InternalErrorException {
        ApiLimit limit = rule.getLimit(appId, url);
        if (limit == null) {
            return true;
        }
        String counterKey = appId + ":" + limit.getApi();
        FixedTimeWinRateLimitAlg retaLimitCounter = counters.get(counterKey);
        /**
         * TODO 这里为什么会double check
         */
        if (retaLimitCounter == null) {
            FixedTimeWinRateLimitAlg newRetaLimitCounter = new FixedTimeWinRateLimitAlg(limit.getLimit());
            retaLimitCounter = counters.putIfAbsent(url, newRetaLimitCounter);
            if (retaLimitCounter == null) {
                retaLimitCounter = newRetaLimitCounter;
            }
        }
        return retaLimitCounter.tryAcquire();
    }

    public static void main(String[] args) {
        RateLimiter limiter = new RateLimiter();
        System.out.println("hello world!");
    }
}
