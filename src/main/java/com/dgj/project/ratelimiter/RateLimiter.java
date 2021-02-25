package com.dgj.project.ratelimiter;

import com.dgj.project.ratelimiter.rule.*;

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

    public RateLimiter() {
    }

    public boolean limit(String appId, String url) {
        return false;
    }
}
