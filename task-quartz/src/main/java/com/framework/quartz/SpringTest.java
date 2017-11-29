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
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * spring的测试类
 * Created by HR on 2017/10/12.
 */
/*@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"classpath*:application-*.xml"})
@WebAppConfiguration*/
public class SpringTest /*extends AbstractTransactionalJUnit4SpringContextTests */{
//继承AbstractTransactionalJUnit4SpringContextTests来获取Spring上下文环境来获取Bean
   /* @Autowired
    private MessageProducer messageProducer;
*/
    @Test
    public void testRabbitMq(){

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-*.xml");

        MessageProducer messageProducer = (MessageProducer) context.getBean("messageProducer");
        /*int x = 10;

        while (x > 0){
            messageProducer.sendMessage("Hello, I am amq sender num :" + x--);
            try {
                //暂停一下，好让消息消费者去取消息打印出来
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

    }

}
