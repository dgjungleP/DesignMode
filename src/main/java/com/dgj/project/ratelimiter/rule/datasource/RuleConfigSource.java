package com.dgj.project.ratelimiter.rule.datasource;

import com.dgj.project.ratelimiter.rule.RuleConfig;

public interface RuleConfigSource {
    RuleConfig load();
}
