package com.framework.quartz;

import com.framework.quartz.rabbitmq.MessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * spring的测试类
 * Created by HR on 2017/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:application-*.xml", "classpath:mvc.xml"})
@WebAppConfiguration
public class SpringTest /*extends AbstractTransactionalJUnit4SpringContextTests */{
//继承AbstractTransactionalJUnit4SpringContextTests来获取Spring上下文环境来获取Bean
    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void testRabbitMq(){
        /*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-*.xml");

        MessageProducer messageProducer2 = (MessageProducer) context.getBean("messageProducer");*/
        int x = 10;

        while (x > 0){
            messageProducer.sendMessage("Hello, I am amq sender num :" + x--);
            try {
                //暂停一下，好让消息消费者去取消息打印出来
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
