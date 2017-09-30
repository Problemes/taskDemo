package com.framework.quartz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 项目启动把基础配置文件读到内存里
 * Created by HR on 2017/9/28.
 */
public class PropertiesHolder {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesHolder.class);

    private static Properties properties = new Properties();

    public static String get(String key){
        return properties.getProperty(key);
    }

    static {
        InputStream in = null;

        try {
            logger.info("Init base Properties ...");

            in = PropertiesHolder.class.getClassLoader().getResourceAsStream("application.properties");
            if (in != null){
                logger.info("inputStream is loading...");
                properties.load(in);
            }

            logger.info("loaded base Properties ...");
        } catch (IOException e) {
            logger.error("Load application.properties error", e.getMessage());
        }finally {
            if (in != null){
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    logger.error("InputStream close Error:", e.getMessage());
                }
            }
        }
    }

}
