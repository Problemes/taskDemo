package com.framework.redis.util;


import java.io.InputStream;
import java.util.Properties;

/**
 * Created by HR on 2017/9/21.
 */
public class PropertiesUtil {

    /**
     * 获取配置文件
     *
     * @return
     * @throws Exception
     */
    public static Properties getProperties(String propertyPath) throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
//            inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyPath);
            inputStream = RedisClient.class.getClassLoader().getResourceAsStream(propertyPath);
            if (inputStream == null)
                //inputStream = this.getClass().getResourceAsStream(propertyPath);
                inputStream = RedisClient.class.getResourceAsStream(propertyPath);

            if (inputStream == null)
                throw new Exception("config file:" + propertyPath + " not found!");
            else
                properties.load(inputStream);

            System.out.println(properties.getProperty("redis.addr"));
            System.out.println(properties.getProperty("redis.port"));
            System.out.println(properties.getProperty("redis.auth"));
            System.out.println(properties.getProperty("timeOut"));

        } finally {
            if (inputStream != null)
                inputStream.close();
        }
        return properties;
    }

}
