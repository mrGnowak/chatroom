package com.chat.chatroom.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapSessionRepository {
    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository();
    }

}
