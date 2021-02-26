package com.dgj.project.ratelimiter.rule;

import java.util.List;

/**
 * @version: v1.0
 * @date: 2021/2/25
 * @author: dgj
 * 对应整个配置文件信息
 */
public class RuleConfig {

    private List<AppRuleConfig> configs;

    public List<AppRuleConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AppRuleConfig> configs) {
        this.configs = configs;
    }

    /**
     * 对应配置文件中的app配置信息
     */
    public static class AppRuleConfig{
        private String appId;
        private List<ApiLimit> limits;

        public AppRuleConfig() {
        }

        public AppRuleConfig(String appId, List<ApiLimit> apiLimits) {
            this.appId = appId;
            this.limits = apiLimits;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public List<ApiLimit> getLimits() {
            return limits;
        }

        public void setLimits(List<ApiLimit> limits) {
            this.limits = limits;
        }
    }
}
