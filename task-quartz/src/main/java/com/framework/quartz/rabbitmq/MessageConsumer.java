package com.framework.quartz.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 功能概要：接收消息
 * Created by HR on 2017/10/12.
 */
public class MessageConsumer implements MessageListener{

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Override
    public void onMessage(Message message) {

        logger.info("receiving messages----->>>:{}", message);

    }
}
