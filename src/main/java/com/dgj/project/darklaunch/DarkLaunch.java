package com.dgj.project.darklaunch;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version: v1.0
 * @date: 2021/3/2
 * @author: dgj
 * 顶层类，负责组装和串联整个操作流程并对外部提供调用接口
 * @see DarkRule
 * @see DarkFeature
 * @see DarkRuleConfig
 */
public class DarkLaunch {
    private static final Logger log = LoggerFactory.getLogger(DarkLaunch.class);
    private static final int DEFAULT_RULE_UPDATE_TIME_INTERVAL = 60;
    private DarkRule rule = new DarkRule();
    private ScheduledExecutorService executor;

    public DarkLaunch(int ruleUpdateTimeInterval) {
        loadRule();
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleAtFixedRate(this::loadRule, ruleUpdateTimeInterval, ruleUpdateTimeInterval, TimeUnit.SECONDS);
    }

    public DarkLaunch() {
        this(DEFAULT_RULE_UPDATE_TIME_INTERVAL);
    }

    /**
     * 负责加载灰度规则
     */
    private void loadRule() {
        InputStream in = null;
        DarkRuleConfig ruleConfig = null;
        try {
            in = this.getClass().getResourceAsStream("/DarkLaunch-rule.yml");
            if (in != null) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(in, DarkRuleConfig.class);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("close file error:{}", e);
                }
            }
        }
        if (ruleConfig == null) {
            throw new RuntimeException("Can not load dark rule.");
        }
        Map<String, IDarkFeature> darkFeatureMap = new HashMap<>();
        List<DarkRuleConfig.DarkFeatureConfig> features = ruleConfig.getFeatures();
        for (DarkRuleConfig.DarkFeatureConfig feature : features) {
            darkFeatureMap.put(feature.getKey(), new DarkFeature(feature));
        }
        this.rule.setDarkFeatureMap(darkFeatureMap);
    }

    /**
     * 对外暴露的接口用户获取到指定的灰度规则
     *
     * @param featureKey 灰度规则的key值
     * @return 当前获取到的指定功能的灰度规则
     * @see IDarkFeature
     */
    public IDarkFeature getDarkFeature(String featureKey) {
        return this.rule.getDarkFeature(featureKey);
    }

    /**
     * 对外暴露的接口用户添加用户自定义的灰度规则
     *
     * @param featureKey
     * @param darkFeature
     */
    public void addProgrammerDarkFeatureMap(String featureKey, IDarkFeature darkFeature) {
        this.rule.addProgrammerDarkFeatureMap(featureKey, darkFeature);
    }
}
