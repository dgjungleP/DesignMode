package com.dgj.project.ratelimiter.rule.parser;

import com.dgj.project.ratelimiter.rule.RuleConfig;

import java.io.InputStream;

/**
 * @version: v1.0
 * @date: 2021/2/26
 * @author: dgj
 */
public interface RuleConfigParser {
    RuleConfig parse(String configText);

    RuleConfig parse(InputStream in);
}
