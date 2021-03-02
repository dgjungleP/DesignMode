package com.dgj.project.darklaunch;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import org.apache.commons.lang.StringUtils;

/**
 * @version: v1.0
 * @date: 2021/3/2
 * @author: dgj
 */
public class DarkFeature {
    private String key;
    private boolean enabled;
    private int percentage;
    private RangeSet<Long> rangeSet = TreeRangeSet.create();

    public DarkFeature(DarkRuleConfig.DarkFeatureConfig feature) {
        this.key = feature.getKey();
        this.enabled = feature.isEnabled();
        String darkRule = feature.getValue().trim();
        parseDarkRule(darkRule);
    }

    @VisibleForTesting
    protected void parseDarkRule(String darkRule) {
        if (!darkRule.startsWith("{") || !darkRule.endsWith("}")) {
            throw new RuntimeException("Failed to parse dark rule :" + darkRule);
        }
        String[] rules = darkRule.substring(1, darkRule.length() - 1).split(",");
        this.rangeSet.clear();
        this.percentage = 0;
        for (String rule : rules) {
            rule = rule.trim();
            if (StringUtils.isEmpty(rule)) {
                continue;
            }
            if (rule.startsWith("%")) {
                int newPercentage = Integer.parseInt(rule);
                if (newPercentage > this.percentage) {
                    this.percentage = newPercentage;
                }
            } else if (rule.contains("-")) {
                String[] parts = rule.split("-");
                if (parts.length != 2) {
                    throw new RuntimeException("Failed to parse dark rule: " + rule);
                }
                ;
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                if (start > end) {
                    throw new RuntimeException("Failed to parse dark rule: " + rule);
                }
                this.rangeSet.add(Range.closed(start, end));
            } else {
                long value = Long.parseLong(rule);
                this.rangeSet.add(Range.closed(value, value));
            }
        }

    }

    public boolean enable() {
        return this.enabled;
    }

    public boolean dark(long darkTarget) {
        boolean selected = this.rangeSet.contains(darkTarget);
        if (selected) {
            return true;
        }
        long reminder = darkTarget % 100;
        if (reminder >= 0 && reminder < this.percentage) {
            return true;
        }
        return false;
    }

    public boolean dark(String darkTarget) {
        return dark(Long.parseLong(darkTarget));
    }

}
