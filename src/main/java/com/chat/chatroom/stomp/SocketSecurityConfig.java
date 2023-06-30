package com.chat.chatroom.stomp;

import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketSecurity
public class SocketSecurityConfig {
    //
    // @Override
    // protected void configureInbound(MessageMatcherDelegatingAuthorizationManager
    // messages) {
    // messages
    // .simpDestMatchers("/secured/**").authenticated()
    // .anyMessage().authenticated();
    // }

    //

    // @Bean
    // public AuthorizationManager<Message<?>>
    // messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder
    // messages) {
    // messages
    // .nullDestMatcher().authenticated()
    // .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
    // .simpDestMatchers("/app/**").hasRole("USER")
    // .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")
    // .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
    // .anyMessage().denyAll();
    //
    // return messages.build();
    // }

    // @Override
    // protected void configureInbound(MessageMatcherDelegatingAuthorizationManager
    // messages) {
    // messages
    // .simpDestMatchers("/secured/**").authenticated()
    // .anyMessage().authenticated();
    // }
    // extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry config) {
    // config.enableSimpleBroker("/topic");
    // config.setApplicationDestinationPrefixes("/app");
    // }
    //
    // @Override
    // public void registerStompEndpoints(StompEndpointRegistry registry) {
    // registry.addEndpoint("/websocket").setHandshakeHandler(new
    // MyHandshakeHandler()).setAllowedOrigins("*")
    // .withSockJS();
    // }
    //
    // public class MyHandshakeHandler extends DefaultHandshakeHandler {
    //
    // @Override
    // protected Principal determineUser(ServerHttpRequest request, WebSocketHandler
    // wsHandler,
    // Map<String, Object> attributes) {
    // // TODO Auto-generated method stub
    // return super.determineUser(request, wsHandler, attributes);
    // }
    //
    // }
    //
    // @Override
    // protected void configureInbound(
    // MessageSecurityMetadataSourceRegistry messages) {
    // messages
    // .simpDestMatchers("/secured/**").authenticated()
    // .anyMessage().authenticated();
    // }
}
