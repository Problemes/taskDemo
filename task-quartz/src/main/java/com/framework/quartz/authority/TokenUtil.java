package com.framework.quartz.authority;

import java.util.*;

/**
 * Created by HR on 2017/7/4.
 */
public class TokenUtil {


    public static String authentication(Map<String , Object > srcData) throws Exception {

        if (srcData == null){
            throw new Exception("加密参数为空！");
        }

        List<Map.Entry<String ,Object>> list = new ArrayList<Map.Entry<String, Object>>(srcData.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            //按照key升序排序
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String ,Object> me : list){
            sb.append(String.valueOf(me.getValue()));
        }
        System.out.println("加密前字串：" + sb);

        String token = MD5Util.MD5String(sb.toString());

        return token;
    }
}
