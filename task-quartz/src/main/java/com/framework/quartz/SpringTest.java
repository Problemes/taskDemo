package com.framework.quartz;

import com.framework.quartz.rabbitmq.MessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * spring的测试类
 * Created by HR on 2017/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:application-*.xml"})
public class SpringTest {

    @Resource(name = "messageProducer")
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
