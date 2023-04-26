package com.chat.chatroom.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // @Autowired
    // protected SocketTextHandler socketTextHandler;
    //
    // @Override
    // public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    // registry.addHandler(socketTextHandler,
    // "/ws/messages").setAllowedOrigins("*");
    // }

    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry registry) {
    // registry.enableStompBrokerRelay("/topic")
    // .setRelayHost("localhost")
    // .setRelayPort(61613)
    // .setClientLogin("guest")
    // .setClientPasscode("guest");
    // registry.setApplicationDestinationPrefixes("/app");
    // }
    //
    // @Override
    // public void registerStompEndpoints(StompEndpointRegistry registry) {
    // registry.addEndpoint("/websocket").withSockJS();
    // }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/topic").setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}