package com.framework.quartz.excel;

/**
 * 导入导出中会出现各种各样的问题，比如：数据源为空、有重复行等，自定义一个ExcelException异常类，用来处理这些问题
 * Created by HR on 2018/3/21.
 */
public class ExcelException extends Exception {

    public ExcelException() {
        // TODO Auto-generated constructor stub
    }

    public ExcelException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public ExcelException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
