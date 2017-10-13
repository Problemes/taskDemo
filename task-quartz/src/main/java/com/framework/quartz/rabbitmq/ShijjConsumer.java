package com.framework.quartz.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 接受topic的消息，可理解为组播，给匹配的一组广播消息
 * 他的exchange为exchangeTest2,  pattern为shijj.*..          .*可以匹配一个, .#可以匹配一个或多个..
 * Created by HR on 2017/10/12.
 */
public class ShijjConsumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ShijjConsumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("shijj receive message------->:{}", message);
    }
}
