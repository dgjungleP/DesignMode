package com.dgj.project.darklaunch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version: v1.0
 * @date: 2021/3/2
 * @author: dgj
 */
public class DarkRule {
    private Map<String, IDarkFeature> darkFeatureMap = new HashMap<>();

    private ConcurrentHashMap<String, IDarkFeature> programmerDarkFeatureMap = new ConcurrentHashMap();

    public DarkRule() {
    }

    public DarkRule(DarkRuleConfig ruleConfig) {
        List<DarkRuleConfig.DarkFeatureConfig> features = ruleConfig.getFeatures();
        for (DarkRuleConfig.DarkFeatureConfig feature : features) {
            darkFeatureMap.put(feature.getKey(), new DarkFeature(feature));
        }
    }

    public void addProgrammerDarkFeatureMap(String featureKey, IDarkFeature darkFeature) {
        this.programmerDarkFeatureMap.put(featureKey, darkFeature);
    }

    public void setDarkFeatureMap(Map<String, IDarkFeature> darkFeatureMap) {
        this.darkFeatureMap = darkFeatureMap;
    }

    public IDarkFeature getDarkFeature(String featureKey) {
        IDarkFeature feature = programmerDarkFeatureMap.get(featureKey);
        if (feature != null) {
            return feature;
        }
        return darkFeatureMap.get(featureKey);
    }
}
