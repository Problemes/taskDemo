package com.framework.quartz.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能概要：消息生产者，提交到队列中去
 * Created by HR on 2017/10/12.
 */
@Service
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Resource(name = "amqpTemplate2")
    private AmqpTemplate amqpTemplate2;

    /*@Resource(name = "template")
    private AmqpTemplate template;*/

    public void sendMessage(Object message) {

        logger.info("sending message...", message);

        amqpTemplate.convertAndSend("queueTestKey", message);
        amqpTemplate.convertAndSend("queueTestChris", message);
        amqpTemplate2.convertAndSend("shijj.xxxx.wsdwd", message);
//        template.convertAndSend("queue_osm", message);

        logger.info("send message end...");
    }

}

