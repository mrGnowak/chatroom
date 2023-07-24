package com.chat.chatroom.controller;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chat.chatroom.model.ChatMessage;
import com.chat.chatroom.repo.ChatMessageRepo;

@Controller
public class ChatControllerStomp {

    private static final Logger logger = LoggerFactory.getLogger(ChatControllerStomp.class);

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public String greet(String greeting) {
        logger.info("wiadomość testowa dla " + greeting);

        String text = "[" + Instant.now() + "]: " + greeting;
        return text;
    }

    @MessageMapping("/sendPublic")
    @SendTo("/topic/sendPublic")
    public ChatMessage sendPublicMessage(ChatMessage message) {
        logger.info("wiadomość publiczna: " + message);
        chatMessageRepo.save(message);
        return message;
    }
}