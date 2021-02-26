package com.dgj.project.ratelimiter.rule.datasource;

import com.dgj.project.ratelimiter.rule.RuleConfig;
import com.dgj.project.ratelimiter.rule.parser.JsonRuleConfigParser;
import com.dgj.project.ratelimiter.rule.parser.RuleConfigParser;
import com.dgj.project.ratelimiter.rule.parser.YamlRuleConfigParser;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @version: v1.0
 * @date: 2021/2/26
 * @author: dgj
 */
public class FileRuleConfigSource implements RuleConfigSource {
    private static final Logger log = LoggerFactory.getLogger(FileRuleConfigSource.class);

    public static final String API_LIMIT_CONFIG_NAME = "RateLimiter-config";
    public static final String YAML_EXTENSION = "yaml";
    public static final String YML_EXTENSION = "yml";
    public static final String JSON_EXTENSION = "json";

    public static final String[] SUPPORT_EXTENSIONS = new String[]{
            YML_EXTENSION,
            YAML_EXTENSION,
            JSON_EXTENSION
    };
    public static final Map<String, RuleConfigParser> PARSER_MAP = new HashMap<>();

    static {
        PARSER_MAP.put(YAML_EXTENSION, new YamlRuleConfigParser());
        PARSER_MAP.put(YML_EXTENSION, new YamlRuleConfigParser());
        PARSER_MAP.put(JSON_EXTENSION, new JsonRuleConfigParser());
    }

    @Override
    public RuleConfig load() {
        for (String extension : SUPPORT_EXTENSIONS) {
            InputStream configStream = null;
            try {
                configStream = this.getClass().getResourceAsStream("/" + getFileNameByExt(extension));
                if (configStream != null) {
                    return PARSER_MAP.get(extension).parse(configStream);
                }
            } finally {
                if (configStream != null) {
                    try {
                        configStream.close();
                    } catch (IOException e) {
                        log.error("close file error:{}", e);
                    }
                }
            }
        }
        return null;
    }

    private String getFileNameByExt(String extension) {
        return API_LIMIT_CONFIG_NAME + '.' + extension;
    }
}
