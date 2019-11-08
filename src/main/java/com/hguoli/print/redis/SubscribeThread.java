package com.hguoli.print.redis;

import com.hguoli.print.ConfigCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SubscribeThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscribeThread.class);

    private final JedisPool jedisPool;
    private final PrintSubscriber subscriber = new PrintSubscriber();

    public SubscribeThread(JedisPool jedisPool) {
        super("SubscribeThread");
        this.jedisPool = jedisPool;
    }

    @Override
    public void run() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.subscribe(subscriber, ConfigCache.CACHE.getRedisChannelName());
        } catch (Exception e) {
            LOGGER.error("Error to subscribe channel:" + ConfigCache.CACHE.getRedisChannelName(), e);
        }
    }
}
