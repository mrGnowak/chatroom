package com.chat.chatroom.ws;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import jakarta.websocket.Session;

@Configuration
public class ChannelInterceptorAdapter {

    @Bean
    public ChannelInterceptorAdapter sessionContextChannelInterceptorAdapter() {
        return new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                Map<String, Object> sessionHeaders = SimpMessageHeaderAccessor
                        .getSessionAttributes(message.getHeaders());
                String sessionId = (String) sessionHeaders.get(SESSION_ATTR);
                if (sessionId != null) {
                    Session session = sessionRepository.getSession(sessionId);
                    if (session != null) {

                        sessionRepository.save(session);
                    }
                }
                return super.preSend(message, channel);
            }
        };
    }
}
