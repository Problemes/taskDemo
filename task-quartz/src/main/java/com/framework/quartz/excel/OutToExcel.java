package com.framework.quartz.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HR on 2018/3/21.
 */
public class OutToExcel {

    /**
     * 将List集合中的对象导入到Excel文件中
     *
     * @param bean :实体类对象的ArrayList集合
     * @param str  : Excel文件的路径
     * @author ：sb
     * @date ：2017年4月6日 上午9:19:16
     */

    public static void excelOut(ArrayList<Object> bean, String str) {

        WritableWorkbook book = null;// 编写WritableWorkbook对象，该对象代表了excel对象

        try {

            book = Workbook.createWorkbook(new File(str)); // 创建文件路径str

            WritableSheet sheet = book.createSheet("sheet", 0);// 获取sheet对象

            // 对集合进行遍历

            for (int i = 0; i < bean.size(); i++) {

                Object ob = bean.get(i);// 集合中的对象不确定，用Object代替

                // 利用反射机制

                Class cl = ob.getClass();// 运行时获得传递过来的对象

                Field[] fi = cl.getDeclaredFields();// 获取所有属性的对象，用来获取属性

                for (int j = 0; j < fi.length; j++) {// 将获得的对象遍历出来

                    fi[j].setAccessible(true);// 启用访问权限

                    // 获取值 列（j），行（i），值fi[j]为字符串方式

                    Label la = new Label(j, i, String.valueOf(fi[j].get(ob)));

                    sheet.addCell(la);// 将数据写入sheet对象中
                }
            }

            book.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                book.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * javaBean 转 Map
     *
     * @param object 需要转换的javabean
     * @return 转换结果map
     * @throws Exception
     */

    public static Map<String, Object> beanToMap(Object object) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();


        Class cls = object.getClass();

        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true);

            map.put(field.getName(), field.get(object));

        }

        return map;

    }


    /**
     * @param map 需要转换的map
     * @param cls 目标javaBean的类对象
     * @return 目标类object
     * @throws Exception
     */

    public static Object mapToBean(Map<String, Object> map, Class cls) {

        Object object;

        try {

            object = cls.newInstance();

            for (String key : map.keySet()) {

                Field temFiels = cls.getDeclaredField(key);

                temFiels.setAccessible(true);

                if (temFiels.getType().toString().equals("class java.util.Date")) {

                    temFiels.set(object, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get(key).toString()));

                } else {

                    temFiels.set(object, map.get(key));

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

        return object;

    }


    public static void main(String[] args) throws IOException, BiffException {

        try {

            InputStream is = new FileInputStream("D://test.xls");

            // 指定Excel的列名称

            List<Map<String, Object>> list = getDataFromExcel(is, 0, new String[]{"membername", "memberid", "membercardid", "points", "operationtype", "changetime"});

            // 不指定Excel的列名称

            // List<Map<String, Object>> list = getDataFromExcel(is, 0, null);

            for (int i = 0; i < list.size(); i++) {

                System.out.println(list.get(i));

                /**

                 * 打印出的结果({changetime=2017-02-15 19:01:57, membercardid=2017021517585890892074,

                 * operationtype=消费获取, membername=李白, memberid=C90B78E8AB8E4F43B27D084F285364B1, points=21})

                 */

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }


    /**
     * 将Excel 中的数据导入到List<Map<String, Object>>中 说明：只支持 xls后缀结尾的excel文件（Excel2003）
     *
     * @param input      指定excel文件输入流
     * @param index      工作博的序号 (从0开始)
     * @param columnName (注意：顺序需要保持一致) : 列名称（对应数据库中的字段） 如果想用excel中原始的列名称，只需传入null即可
     */

    public static List<Map<String, Object>> getDataFromExcel(InputStream input, Integer index, String[] columnName) throws IOException, BiffException {

        // 定义文本簿

        Workbook rwb = null;

        // 定义容器

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        // 定义容器存储列名称

        List<String> columnList = new ArrayList<String>();


        try {

            rwb = Workbook.getWorkbook(input);

            if (index == null || index > rwb.getSheets().length || index < 0) {

                index = 0;

            }

            // 获得第一个工作表对象

            Sheet sheet = rwb.getSheet(index);

            int rows = sheet.getRows();

            int columns = sheet.getColumns();

            for (int i = 0; i < rows; i++) {

                Map<String, Object> map = new HashMap<String, Object>();

                for (int j = 0; j < columns; j++) {

                    // 表示获取第i行第j列

                    Cell cell = sheet.getCell(j, i);

                    String result = cell.getContents();

                    // 存储列名称

                    if (i == 0 && columnName == null) {

                        columnList.add(result);

                    } else {

                        if (columnName == null) {

                            map.put(columnList.get(j), result);

                        } else {

                            map.put(columnName[j], result);

                        }

                    }

                }

                if (i != 0) {

                    list.add(map);

                }

            }

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        } finally {
            if (rwb != null) {
                rwb.close();
            }
        }

        return list;

    }


}
