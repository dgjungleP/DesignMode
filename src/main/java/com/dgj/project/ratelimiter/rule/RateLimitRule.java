package com.dgj.project.ratelimiter.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version: v1.0
 * @date: 2021/2/25
 * @author: dgj
 * 实现用于快速查询规则的数据结构 目前暂时使用HashMap实现 后期可以修改为前缀树
 */
public class RateLimitRule {
    private Map<String, Map<String, ApiLimit>> ruleMap = new HashMap<>();

    public RateLimitRule(RuleConfig ruleConfig) {
        for (RuleConfig.AppRuleConfig config : ruleConfig.getConfigs()) {
            String appId = config.getAppId();
            List<ApiLimit> limits = config.getLimits();
            Map<String, ApiLimit> limitMap = new HashMap<>();
            for (ApiLimit limit : limits) {
                limitMap.put(limit.getApi(), limit);
            }
            ruleMap.put(appId, limitMap);
        }
    }

    public ApiLimit getLimit(String appId, String api) {
        return ruleMap.get(appId).get(api);
    }
}
