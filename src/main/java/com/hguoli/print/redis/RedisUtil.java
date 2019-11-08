package com.hguoli.print.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.Properties;

public enum RedisUtil {

    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
    private JedisPool pool;
    private Map<String,String> keys;

    /**
     * 销毁JedisPool
     */
    public void destroyJedisPool() {
        if (null != pool) {
            pool.destroy();
        }
    }

    /**
     * 初始化JedisPool
     */
    public void initJedisPool(Properties properties) {
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("redis.max.idle")));
            jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.max.wait")));
            jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("redis.max.active")));
            jedisPoolConfig.setMinIdle(Integer.parseInt(properties.getProperty("redis.min.idle")));
            String pwdStr = properties.getProperty("redis.password");
            String pwd = (null == pwdStr || "".equals(pwdStr)) ? null : pwdStr;
            String host = properties.getProperty("redis.host");
            int port = Integer.parseInt(properties.getProperty("redis.port"));
            int timeout = Integer.parseInt(properties.getProperty("redis.connect.timeout"));
            pool = new JedisPool(jedisPoolConfig, host, port, timeout, pwd);
            LOGGER.info("Init Jedis Pool succeed on " + host + ":" + port);
        } catch (Exception e) {
            LOGGER.error("Init Jedis Pool failed!", e);
        }
    }

    /**
     * Get value by key
     *
     * @param key String
     * @return String
     */
    public String get(String key) {
        if (null != pool) {
            try (Jedis jedis = pool.getResource()) {
                if (null != jedis) {
                    return jedis.get(key);
                }
            } catch (Exception e) {
                LOGGER.error("Error to get value of '" + key + "' from Redis!", e);
                return null;
            }
        }
        return null;
    }

    /**
     * Set value to redis
     *
     * @param key   String
     * @param value String
     */
    public void set(String key, String value) {
        if (null != pool) {
            try (Jedis jedis = pool.getResource()) {
                if (null != jedis) {
                    jedis.set(key, value);
                }
            } catch (Exception e) {
                LOGGER.error("Error to set value '" + value + "' of '" + key + "' to Redis!", e);
            }
        }
    }

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }
}

