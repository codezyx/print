package com.hguoli.print;

import com.hguoli.print.redis.RedisUtil;
import com.hguoli.print.redis.SubscribeThread;
import com.hguoli.print.util.ConfigCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("1. Loading configuration.");
        InputStream resourceAsStream = null;
        try {
            Properties properties = new Properties();
            resourceAsStream = ConfigCache.class.getResourceAsStream("/config.properties");
            properties.load(resourceAsStream);
            ConfigCache.CACHE.load(properties);// 读取配置文件到缓存
            LOGGER.info("---Loaded configuration: " + ConfigCache.CACHE);
            LOGGER.info("2. Initializing jedis pool...");
            RedisUtil.INSTANCE.initJedisPool(properties);// 初始化redis pool
        } catch (Exception e) {
            LOGGER.error("Error to start App!", e);
            System.exit(1);
        } finally {
            if (null != resourceAsStream) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    LOGGER.error("Error to close input stream!", e);
                }
            }
        }
        LOGGER.info("3. Starting redis subscriber.");
        SubscribeThread subscribeThread = new SubscribeThread(RedisUtil.INSTANCE.getPool());
        subscribeThread.start();
    }

}
