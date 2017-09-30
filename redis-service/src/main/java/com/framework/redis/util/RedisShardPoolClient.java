package com.framework.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Jedis分片连接池(分布式)
 * http://blog.csdn.net/lang_man_xing/article/details/38405269?spm=5176.100239.blogcont59712.5.7GnwhO
 * 理论： http://www.cnblogs.com/vhua/p/redis_1.html
 * Created by HR on 2017/9/22.
 */
public class RedisShardPoolClient {

    private static final Logger logger = LoggerFactory.getLogger(RedisShardPoolClient.class);
    private static ShardedJedisPool pool;
    private static String SHARDED_JEDIS_CLIENT_CONFIG_FILE = "redis.properties";

    static {
        try {
            Properties properties = PropertiesUtil.getProperties(SHARDED_JEDIS_CLIENT_CONFIG_FILE);

            JedisPoolConfig config = new JedisPoolConfig();//Jedis池配置

            config.setMaxTotal(Integer.parseInt(properties.getProperty("maxActive")));//最大活动的对象个数
            config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));//对象最大空闲时间
            config.setMaxWaitMillis(Long.parseLong(properties.getProperty("maxWait")));//获取对象时最大等待时间
            config.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("testOnBorrow")));
            //config.setTestOnReturn(false);


            //设置Redis信息
            String hostA = properties.getProperty("redis.hostA");
            int portA = Integer.parseInt(properties.getProperty("redis.portA"));
            String hostB = properties.getProperty("redis.hostB");
            int portB =Integer.parseInt(properties.getProperty("redis.portB"));

            List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(2);

            JedisShardInfo infoA = new JedisShardInfo(hostA, portA);
            //infoA.setPassword(properties.getProperty("redis.authA"));
            JedisShardInfo infoB = new JedisShardInfo(hostB, portB);
            //infoB.setPassword(properties.getProperty("redis.authB"));

            jdsInfoList.add(infoA);
            jdsInfoList.add(infoB);

            //初始化ShardedJedisPool
            pool = new ShardedJedisPool(config, jdsInfoList);

        } catch (Exception e) {
            logger.error("ShardedJedisPool创建失败...", e);
        }
    }

    /**
     * 获取 ShardedJedis
     *
     * @return
     */
    public synchronized ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static class SingletonHolder {
        public static final RedisShardPoolClient instance = new RedisShardPoolClient();
    }

    public static RedisShardPoolClient getInstance() {
        return SingletonHolder.instance;
    }


    /**
     * @param args
     */

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String key = generateKey();
            try (ShardedJedis jds = pool.getResource()) {
                System.out.println(key + ":" + jds.getShard(key).getClient().getHost());
                System.out.println(jds.set(key, Math.random() + ""));
            }
        }
    }

    private static int index = 1;

    public static String generateKey() {
        return String.valueOf(Thread.currentThread().getId()) + "_" + (index++);
    }
}
