package com.dgj.project.ratelimiter;

import com.dgj.project.ratelimiter.alg.RateLimitAlg;
import com.dgj.project.ratelimiter.exception.InternalErrorException;
import com.dgj.project.ratelimiter.rule.ApiLimit;
import com.dgj.project.ratelimiter.rule.RateLimitRule;
import com.dgj.project.ratelimiter.rule.RuleConfig;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
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
 * @see RateLimitRule
 * @see RuleConfig.AppRuleConfig
 */
public class RateLimiter {
    private static final Logger log = LoggerFactory.getLogger(RateLimiter.class);
    /**
     * 为每个api在内存中存储限流计算器
     */
    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();
    private RateLimitRule rule;


    public RateLimiter() {
        RuleConfig ruleConfig = new RuleConfig();
        InputStream configStream = this.getClass().getResourceAsStream("/RateLimiter-config.yml");
        if (configStream != null) {
            Yaml yaml = new Yaml();
            ruleConfig = yaml.loadAs(configStream, RuleConfig.class);
        }
        this.rule = new RateLimitRule(ruleConfig);
    }

    public boolean limit(String appId, String url) throws InternalErrorException {
        ApiLimit limit = rule.getLimit(appId, url);
        if (limit == null) {
            return true;
        }
        String counterKey = appId + ":" + limit.getApi();
        RateLimitAlg retaLimitCounter = counters.get(counterKey);
        /**
         * TODO 这里为什么会double check
         */
        if (retaLimitCounter == null) {
            RateLimitAlg newRetaLimitCounter = new RateLimitAlg(limit.getLimit());
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
