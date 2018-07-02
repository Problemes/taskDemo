package com.framework.quartz.controller;

import com.framework.quartz.socket.SocketHandler;
import com.framework.quartz.util.QRCodeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by HR on 2017/9/25.
 */

@Controller
public class SocketController {

    private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

    @Autowired
    private SocketHandler socketHandler;

    @RequestMapping(value = "loginSocket")
    public String login(HttpServletRequest request, HttpSession session){

        logger.info("用户登录并建立了socket连接");
        Cookie[] cookies = request.getCookies();
        logger.info("Cookies : " + JSONArray.fromObject(cookies).toString());
        logger.debug("JSESSIONID : ",session.getId());
        logger.info("Session:" + JSONObject.fromObject(session));
        session.setAttribute("user",session.getId());

        return "message";
    }

    @RequestMapping(value = "message")
    public String sendMessage(HttpSession session){
        logger.info("用户发送socket测试消息...");

        socketHandler.sendMessageToUser(session.getId(),new TextMessage("socket send message test..." + session.getId()));

        return "success";
    }

    @RequestMapping(value = "qrCode")
    @ResponseBody
    public void sendQRCodeStream(HttpServletResponse response, String channel) throws Exception {

        QRCodeUtil.generateQRCode("http://qq.tunnel.qydev.com/task/login?param1=" + channel, 300, 300, "bmp", response);
    }

}
