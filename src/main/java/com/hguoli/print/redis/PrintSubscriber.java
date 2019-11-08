package com.hguoli.print.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

public class PrintSubscriber extends JedisPubSub {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintSubscriber.class);

    @Override
    public void onMessage(String channel, String message) {
        LOGGER.info("Received message:" + message + " from channel:" + channel);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        LOGGER.info("On subscribe Channel:" + channel + ", total subscribed channel num:" + subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        LOGGER.info("On unsubscribe channel:" + channel + ", total subscribed channel num:" + subscribedChannels);
    }
}
