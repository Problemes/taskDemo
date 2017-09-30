package com.framework.quartz.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置类WebSocketConfig
 * Created by HR on 2017/9/25.
 */

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Autowired
    private SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        //注册处理拦截器,拦截url为socketServer的请求
        registry.addHandler(socketHandler,"/socketServer").addInterceptors(new WebSocketInterceptor());


        //注册处理拦截器,拦截url为socketServer的请求
        registry.addHandler(socketHandler,"/sockjs/socketServer").addInterceptors(new WebSocketInterceptor());
    }
}
