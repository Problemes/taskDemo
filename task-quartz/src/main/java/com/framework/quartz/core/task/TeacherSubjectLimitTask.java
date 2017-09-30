package com.framework.quartz.core.task;

import com.framework.quartz.entities.TeacherSubjectLimit;
import com.framework.quartz.service.TeacherAssessLimitService;
import com.framework.redis.util.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Created by HR on 2017/9/26.
 */

@Component
public class TeacherSubjectLimitTask {

    private static final Logger logger = LoggerFactory.getLogger(TeacherSubjectLimitTask.class);
    RedisClient redisClient = null;

    @Autowired
    private TeacherAssessLimitService teacherAssessLimitService;

    @Scheduled(cron = "0 57 10 * * ?")
    public void setTeacherSubjectLimitTask(){

        Jedis jedis = null;

        try {
            redisClient = RedisClient.getInstance();
            jedis = redisClient.getJedis();
            Pipeline pipeline = jedis.pipelined();

            List<TeacherSubjectLimit> teacherSubjectLimits = teacherAssessLimitService.getTchSubLimits();

            logger.info("读入mysql限制数据...");

            for (TeacherSubjectLimit teacherSubjectLimit : teacherSubjectLimits){
                pipeline.del("teacherLimitTotal_" + teacherSubjectLimit.getTchId() + "_" + teacherSubjectLimit.getSubId());
                pipeline.incrBy("teacherLimitTotal_" + teacherSubjectLimit.getTchId() + "_" + teacherSubjectLimit.getSubId(),teacherSubjectLimit.getLimit());
                pipeline.del("teacherLimit_" + teacherSubjectLimit.getTchId() + "_" + teacherSubjectLimit.getSubId());
                pipeline.incrBy("teacherLimit_" + teacherSubjectLimit.getTchId() + "_" + teacherSubjectLimit.getSubId(),teacherSubjectLimit.getLimit());
            }

            logger.info("写入redis限制数据...");
            pipeline.sync();
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }


    }


}
