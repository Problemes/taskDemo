package com.framework.quartz.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 功能概要：接收消息，另一个接受者Chris
 * (为了测试一个exchange可以发送多个消息), 他的exchange为exchangeTest,  rout-key为queueTestChris.
 * Created by HR on 2017/10/12.
 */
public class ChrisConsumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ChrisConsumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("chris receive message------->:{}", message);
    }
}
