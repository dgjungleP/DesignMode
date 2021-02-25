package com.dgj.project.ratelimiter.rule;

/**
 * @version: v1.0
 * @date: 2021/2/25
 * @author: dgj
 * 对应配置文件中的api的配置信息
 */
public class ApiLimit {
    private static final int DEFAULT_TIME_UNIT = 1;
    /**
     * api名称
     */
    private String api;
    /**
     * 单位时间内的限制
     */
    private Integer limit;
    /**
     * 单位：默认为 1s
     */
    private Integer unit = DEFAULT_TIME_UNIT;

    public ApiLimit(String api, Integer limit, Integer unit) {
        this.api = api;
        this.limit = limit;
        this.unit = unit;
    }

    public ApiLimit(String api, Integer limit) {
        this(api, limit, DEFAULT_TIME_UNIT);
    }

    public ApiLimit() {
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }
}
