package com.framework.quartz;

import com.framework.quartz.rabbitmq.MessageProducer;
import com.framework.quartz.service.GoodsService;
import com.framework.quartz.shiro_2.entity.User;
import com.framework.quartz.util.QRCodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * spring的测试类
 * Created by HR on 2017/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"classpath*:application-*.xml"})
@WebAppConfiguration
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




    @Autowired
    private GoodsService goodsService;

    @Test
    public void testGoods(){

        System.out.println(goodsService.getGoods(1000001l));
    }

    @Test
    public void testUserCreate(){

//        User user = new User();
//
//        user.setUsername("test");
//        user.setPassword("testpw");
//        user.setSalt("test");
//
//        User result = userService.createUser(user);
//
//        System.out.println(result.getId());

    }


}
