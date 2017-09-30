package com.framework.redis.util;

import net.sf.json.JSONArray;
import org.apache.commons.beanutils.BeanUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import java.util.Properties;

/**
 * Created by HR on 2017/9/21.
 */
public class RedisClient {

    private static String JEDIS_CLIENT_CONFIG_FILE = "redis.properties";
    private static JedisPool jedisPool = null;

    private RedisClient() {

        try {
//            Properties properties = this.getProperties(JEDIS_CLIENT_CONFIG_FILE);
            Properties properties = PropertiesUtil.getProperties(JEDIS_CLIENT_CONFIG_FILE);

            JedisPoolConfig config = new JedisPoolConfig();

            //将properties中的配置拷贝到bean中
            BeanUtils.populate(config, properties);

            String host = properties.getProperty("redis.addr");
            int port = Integer.parseInt(properties.getProperty("redis.port"));
            int timeout = Integer.parseInt(properties.getProperty("timeOut"));

            String password = (String) properties.get("redis.auth");

            //创建redis pool
            if (password != null && !"".equals(password.trim())) {

                jedisPool = new JedisPool(config, host, port, timeout, password);
            } else {
                System.out.println("host: " + host);
                System.out.println("port: " + port);
                System.out.println("timeout: " + timeout);
                System.out.println("config: " + JSONArray.fromObject(config));
                jedisPool = new JedisPool(config, host, port, timeout);
            }

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
            if (jedis != null) {
                jedis.close();
            }
        }
        return jedis;
    }

    /**
     * Handle jedisException, write log and return whether the connection is broken.
     * 判断是否是使redis断开连接的异常
     * @param jedisException
     * @return
     */
    protected boolean isBrokenException(JedisException jedisException) {

        if (jedisException instanceof JedisConnectionException) {

            //logger.error("Redis connection " + jedisPool.getAddress() + " lost.", jedisException);
        } else if (jedisException instanceof JedisDataException) {

            if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {

                //logger.error("Redis connection " + jedisPool.getAddress() + " are read-only slave.", jedisException);
            } else {
                // dataException, isBroken=false
                return false;
            }
        } else {

            //logger.error.("Jedis exception happen.", jedisException));
        }
        return true;
    }

    /**
     * 静态内部类,实现单例
     */
    public static class SingletonHolder {

        private static final RedisClient instance = new RedisClient();
    }

    public static RedisClient getInstance() {
        System.out.println("SingletonHolder.instance:" + SingletonHolder.instance);
        return SingletonHolder.instance;
    }

}
