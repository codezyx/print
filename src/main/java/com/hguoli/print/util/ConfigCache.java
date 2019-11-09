package com.hguoli.print.util;

import java.util.Properties;

public enum ConfigCache {

    CACHE;

    private String appHome;
    private String csvFilePath;
    private Integer columnSize;
    private String redisChannelName;

    /**
     * 读取config.properties
     */
    public void load(Properties properties) throws Exception {
        setAppHome(properties.getProperty("app.home"));
        setCsvFilePath(properties.getProperty("csv.file.path"));
        setColumnSize(Integer.parseInt(properties.getProperty("pdf.table.column.size")));
        setRedisChannelName(properties.getProperty("redis.channel.name"));
    }

    public String getAppHome() {
        return appHome;
    }

    public void setAppHome(String appHome) {
        this.appHome = appHome;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }


    public String getRedisChannelName() {
        return redisChannelName;
    }

    public void setRedisChannelName(String redisChannelName) {
        this.redisChannelName = redisChannelName;
    }

    @Override
    public String toString() {
        return "ConfigCache{" +
                "appHome='" + appHome + '\'' +
                ", csvFilePath='" + csvFilePath + '\'' +
                ", columnSize=" + columnSize +
                ", redisChannelName='" + redisChannelName + '\'' +
                '}';
    }
}
