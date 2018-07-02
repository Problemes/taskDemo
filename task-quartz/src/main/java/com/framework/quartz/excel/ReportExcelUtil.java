package com.framework.quartz.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 报表Excel表格操作 工具类
 * Created by HR on 2018/3/21.
 */
public class ReportExcelUtil {

    /**
     * @param jsonArray       数据源
     * @param enTozhHeaderMap 表头的中英文转换map
     * @param path             输出excel位置
     */
    public synchronized static void writeToExcel(JSONArray jsonArray, LinkedHashMap<String, String> enTozhHeaderMap, String path) {

        if (jsonArray.size() == 0 || jsonArray == null) {
            throw new RuntimeException("数据源中没有任何数据");
        }

        //创建工作簿并发送到OutputStream指定的地方
        WritableWorkbook wwb = null;

        try {
            wwb = Workbook.createWorkbook(new File(path)); // 创建文件路径

            // TODO: 2018/3/21  sheet size paging
            WritableSheet sheet = wwb.createSheet("sheet", 0);// 获取sheet对象

            JSONObject firstDataSource = (JSONObject) jsonArray.get(0);
            System.out.println("first object :--->>>" + firstDataSource);

            //填充表头
            int firstRow = 0;
            for (String key : firstDataSource.keySet()) {

                Label label = new Label(firstRow, 0, key);
                sheet.addCell(label);
                firstRow++;
            }

            Iterator datasource = jsonArray.iterator();
            System.out.println("datasource: --->>>" + datasource);

            Label selectLabel;
            int row = 0;

            JSONObject jsonObject;
            while (datasource.hasNext()) {

                jsonObject = (JSONObject) datasource.next();
                row++;
                for (int column = 0; column < firstDataSource.keySet().size(); column++) {

                    selectLabel = (Label) sheet.getCell(column, 0);
                    Label label = new Label(column, row, String.valueOf(jsonObject.get(selectLabel.getContents())));
                    sheet.addCell(label);
                }
            }

            wwb.write();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }finally {
            try {
                wwb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String args[]){

        String path = "D:/zzz/test.xls";

        String str = "[{\"schId\":\"1000001\",\"infoSrc\":\"0\",\"subId\":\"1000001\",\"objType\":\"0\",\"rewardCount\":\"2\"},{\"schId\":\"1000002\",\"infoSrc\":\"2\",\"subId\":\"1000003\",\"objType\":\"0\",\"rewardCount\":\"2\"},{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"},,{\"schId\":\"1000003\",\"infoSrc\":\"0\",\"subId\":\"1000004\",\"objType\":\"0\",\"rewardCount\":\"15\"}]";
        JSONArray jsonArray = JSONArray.parseArray(str);

        System.out.println(jsonArray);
        writeToExcel(jsonArray, null, path);

        new File("D:/zzz/test1.txt");
    }

}
