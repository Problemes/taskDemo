package com.framework.quartz.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * WebSocket处理类SocketHandler
 * Created by HR on 2017/9/25.
 */

@Service
public class SocketHandler implements WebSocketHandler {

    private static final Logger logger;
    private static final ArrayList<WebSocketSession> users;

    static {
        logger = LoggerFactory.getLogger(SocketHandler.class);
        users = new ArrayList<WebSocketSession>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

        logger.info("成功建立socket连接");

        users.add(webSocketSession);
        String username = webSocketSession.getAttributes().get("user").toString();
        if (username != null) {
            webSocketSession.sendMessage(new TextMessage("socket connection success..."));
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        logger.info("socket连接出现错误：" + throwable.getMessage());
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("连接已关闭!");
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {

        for (WebSocketSession session : users) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("user").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
