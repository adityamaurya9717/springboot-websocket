package com.websocket.aditya.config;

import com.websocket.aditya.websocket_interceptor.CustomHandShakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public CustomeSocketHandler myMessageHandler() {

        CustomeSocketHandler customeSocketHandler = new CustomeSocketHandler();
        return customeSocketHandler;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("registry web handler");
        registry.addHandler(myMessageHandler(), "/websocket")
                .addInterceptors(new CustomHandShakeInterceptor());
    }
}
