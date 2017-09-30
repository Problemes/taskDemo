package com.framework.quartz.multiDataSource;

/**
 * Created by HR on 2017/9/21.
 */
public class DynamicDataSourceHolder {

    /**
     * 注意：数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰
     */

    private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>();

    public static String getDataSource(){
        return THREAD_DATA_SOURCE.get();
    }

    public static void setThreadDataSource(String dataSource){
        THREAD_DATA_SOURCE.set(dataSource);
    }

    public static void clearThreadLocal(){
        THREAD_DATA_SOURCE.remove();
    }

}
