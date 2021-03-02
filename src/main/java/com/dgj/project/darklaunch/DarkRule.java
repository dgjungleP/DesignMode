package com.dgj.project.darklaunch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version: v1.0
 * @date: 2021/3/2
 * @author: dgj
 */
public class DarkRule {
    private Map<String, DarkFeature> darkFeatureMap = new HashMap<>();

    public DarkRule(DarkRuleConfig ruleConfig) {
        List<DarkRuleConfig.DarkFeatureConfig> features = ruleConfig.getFeatures();
        for (DarkRuleConfig.DarkFeatureConfig feature : features) {
            darkFeatureMap.put(feature.getKey(), new DarkFeature(feature));
        }
    }

    public DarkFeature getDarkFeature(String featureKey) {
        return darkFeatureMap.get(featureKey);
    }
}
