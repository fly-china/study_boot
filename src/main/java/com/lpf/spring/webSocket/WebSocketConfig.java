package com.lpf.spring.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket的配置类，开启WebSocket
 *
 * @author lipengfei
 * @create 2019-04-17 11:11
 **/
@Configuration
public class WebSocketConfig {


    /**
     * 自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
