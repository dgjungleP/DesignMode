package com.dgj.project.darklaunch;

import java.util.List;

/**
 * @version: v1.0
 * @date: 2021/3/2
 * @author: dgj
 */
public class DarkRuleConfig {
    private List<DarkFeatureConfig> features;

    public List<DarkFeatureConfig> getFeatures() {
        return features;
    }

    public void setFeatures(List<DarkFeatureConfig> features) {
        this.features = features;
    }

    public static class DarkFeatureConfig {
        private String key;
        private boolean enabled;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
