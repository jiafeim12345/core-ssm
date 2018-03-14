package me.cloudcat.develop.entity.vo;

public class ConfigVO {

    private Integer minTime;    // 刷新最小时间
    private Integer maxTime;    // 刷新最大时间
    private String cookie;      // 网站Cookie

    public ConfigVO(Integer minTime, Integer maxTime, String cookie) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.cookie = cookie;
    }

    public ConfigVO() {}

    public Integer getMinTime() {
        return minTime;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
