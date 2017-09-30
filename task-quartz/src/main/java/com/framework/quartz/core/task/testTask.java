package com.framework.quartz.core.task;

import com.framework.quartz.socket.SocketHandler;
import com.framework.redis.util.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import redis.clients.jedis.Jedis;


/**
 * Created by HR on 2017/9/19.
 */
@Component
public class testTask {

    Logger log = LoggerFactory.getLogger(testTask.class);

    RedisClient redisClient = null;

    @Autowired
    private SocketHandler socketHandler;

//    private SocketHandler socketHandler = new SocketHandler();

    //@Scheduled(fixedDelay = 5000)
    public void testScheduler() {

        redisClient = RedisClient.getInstance();

        Jedis jedis = redisClient.getJedis();

        try{
            socketHandler.sendMessageToUsers(new TextMessage("socket send message Task System test..."));

            log.info("开始测试定时任务...debug级别");
            log.info("开始测试定时任务...info级别");

            System.out.println("Redis Set:" + jedis.setex("wori",60, String.valueOf(Math.random())));
            System.out.println("我是定时任务，我5s打印一次！");
            System.out.println("Redis测试：" + jedis.get("wori"));

            log.info("结束测试定时任务...debug级别");
            log.info("结束测试定时任务...info级别");

        }catch (Exception e){
            e.getMessage();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

}
