package com.hguoli.print;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public enum ConfigCache {

    CACHE;

    private String pdfTmpDir;
    private String csvFilePath;

    private String redisHost;
    private Integer redisPort = 6379;
    private String redisPassword;
    private Integer redisTimeout;
    private Integer redisMaxActive;
    private Integer redisMaxWait;
    private Integer redisMaxIdle;
    private Integer redisMinIdle;

    /**
     * 读取config.properties
     */
    public void load() throws Exception {
        Properties properties = new Properties();
        String file = System.getenv("APP_HOME") + File.separator + "conf" + File.separator + "config.properties";
        FileInputStream fileInputStream = new FileInputStream(file);
        properties.load(fileInputStream);
        fileInputStream.close();
        setPdfTmpDir(properties.getProperty("pdf.tmp.dir"));
        setCsvFilePath(properties.getProperty("csv.file.path"));
        setRedisHost(properties.getProperty("redis.host"));
        setRedisPassword(properties.getProperty("redis.password"));
        setRedisMaxActive(Integer.parseInt(properties.getProperty("redis.pool.max-active")));
        setRedisMaxWait(Integer.parseInt(properties.getProperty("redis.pool.max-wait")));
        setRedisMaxIdle(Integer.parseInt(properties.getProperty("redis.pool.max-idle")));
        setRedisMinIdle(Integer.parseInt(properties.getProperty("redis.pool.min-idle")));
    }

    public String getPdfTmpDir() {
        return pdfTmpDir;
    }

    public void setPdfTmpDir(String pdfTmpDir) {
        this.pdfTmpDir = pdfTmpDir;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public Integer getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(Integer redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public Integer getRedisTimeout() {
        return redisTimeout;
    }

    public void setRedisTimeout(Integer redisTimeout) {
        this.redisTimeout = redisTimeout;
    }

    public Integer getRedisMaxActive() {
        return redisMaxActive;
    }

    public void setRedisMaxActive(Integer redisMaxActive) {
        this.redisMaxActive = redisMaxActive;
    }

    public Integer getRedisMaxWait() {
        return redisMaxWait;
    }

    public void setRedisMaxWait(Integer redisMaxWait) {
        this.redisMaxWait = redisMaxWait;
    }

    public Integer getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public void setRedisMaxIdle(Integer redisMaxIdle) {
        this.redisMaxIdle = redisMaxIdle;
    }

    public Integer getRedisMinIdle() {
        return redisMinIdle;
    }

    public void setRedisMinIdle(Integer redisMinIdle) {
        this.redisMinIdle = redisMinIdle;
    }

}