package com.dgj.project.ratelimiter.rule;

public interface RateLimitRule {
    ApiLimit getLimit(String appId, String api);
}
