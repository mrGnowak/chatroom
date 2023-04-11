package com.chat.chatroom.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    protected SocketTextHandler socketTextHandler;

    @Autowired
    ChannelInterceptorAdapter channelInterceptorAdapter;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketTextHandler, "/ws/messages")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionIdHandshakeInterceptor());
    }

    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelInterceptorAdapter.sessionContextChannelInterceptorAdapter());
    }

}