package com.framework.quartz.controller;

import com.framework.http.core.HttpClientUtils;
import com.framework.http.core.HttpUtil;
import io.goeasy.GoEasy;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by HR on 2018/4/12.
 */

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("login")
    public String login(HttpServletRequest request, HttpSession session) {

        String param = request.getParameter("param1");
        System.out.println("qr Login param:" + param);

        Cookie[] cookies = request.getCookies();
        logger.info("Cookies : " + JSONArray.fromObject(cookies).toString());
        logger.debug("JSESSIONID : ", session.getId());
        logger.info("Session:" + JSONObject.fromObject(session));

        request.setAttribute("param", param);

        return "login";
    }

    @RequestMapping("loginVerify")
    @ResponseBody
    public Map<String, Object> loginVerify(HttpServletRequest request) {

        logger.info("loginVerify starting...");

        String param = request.getParameter("param");
        String verify = request.getParameter("verify");

        GoEasy goEasy = new GoEasy("BC-d3a3a7f09f3f4aee95fe82934c20bd4c");

        Map<String, Object> map = new HashedMap();

        if (verify.equals("0")) {
            goEasy.publish(param, "0");
            map.put("result", "0");
        } else {
            goEasy.publish(param, "1");
            map.put("result", "1");
        }

        logger.info("loginVerify:" + param + ":" + verify + ":" + map.get("result"));

        return map;
    }

    @RequestMapping("/wechat/getCode")
    @ResponseBody
    public void weixinLogin() throws Exception {
        //拼接url
        StringBuilder url = new StringBuilder();
        url.append("https://open.weixin.qq.com/connect/qrconnect?");
        //appid
        url.append("appid=" + "appid");
        //回调地址 ,回调地址要进行Encode转码
        String redirect_uri = "192.168.1.133:8888/task/wechat/callback"; //转码
        url.append("&redirect_uri=" + URLEncoder.encode(redirect_uri, "UTF-8"));
        url.append("&response_type=code");
        url.append("&scope=snsapi_login");
        HttpUtil.doGet(url.toString());
    }

    @RequestMapping("/wechat/callback")
    @ResponseBody
    public void weixinLoginCallBack(String code, String state) throws Exception {

        System.out.println("====" + code + "===" + state + "====");
        if (StringUtils.isNotEmpty(code)) {
            StringBuilder url = new StringBuilder();
            url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
            url.append("appid=" + "appid");
            url.append("&secret=" + "secret");

            //这是微信回调给你的code
            url.append("&code=" + code);
            url.append("&grant_type=authorization_code");
        }
//        return new ModelAndView("login");
    }


    @RequestMapping("/wechat/gz/getCode")
    @ResponseBody
    public Map<String, Object> weixinGZLogin(String code, String state, String uuid) throws Exception {

        System.out.println("weixinGZCallbackParams:" + code + "---===---" + state);
        System.out.println("back uuid:" + uuid);
        //拼接url
        StringBuilder url = new StringBuilder();
        url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");

        url.append("appid=" + "wxe0828ea2f2e4648f");
        url.append("&secret=" + "63afe81bf139954941808aee7d60d089");
        url.append("&code=" + code);
        url.append("&grant_type=" + "authorization_code");

        String result = HttpClientUtils.get(url.toString());

        GoEasy goEasy = new GoEasy("BC-d3a3a7f09f3f4aee95fe82934c20bd4c");

        Map<String, Object> map = new HashedMap();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "0");
        jsonObject.put("openid", JSONObject.fromObject(result).get("openid"));

        goEasy.publish(uuid, jsonObject.toString());

        map.put("result", "0");


        System.out.println("回调获取AccessToken：" + result);

        return map;
    }

}
