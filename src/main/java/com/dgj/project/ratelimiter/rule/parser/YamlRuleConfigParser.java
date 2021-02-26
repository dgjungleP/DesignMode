package com.dgj.project.ratelimiter.rule.parser;

import com.dgj.project.ratelimiter.rule.RuleConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * @version: v1.0
 * @date: 2021/2/26
 * @author: dgj
 */
public class YamlRuleConfigParser implements RuleConfigParser {
    @Override
    public RuleConfig parse(String configText) {
        return null;
    }

    @Override
    public RuleConfig parse(InputStream in) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(in, RuleConfig.class);
    }
}
