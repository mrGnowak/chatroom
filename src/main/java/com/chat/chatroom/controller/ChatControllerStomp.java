package com.chat.chatroom.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chat.chatroom.model.ChatMessage;
import com.chat.chatroom.repo.ChatMessageRepo;

@Controller
public class ChatControllerStomp {

  private static final Logger logger = LoggerFactory.getLogger(ChatControllerStomp.class);

  @Autowired
  private ChatMessageRepo chatMessageRepo;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  // @MessageMapping("/test")
  // @SendTo("/topic/test")
  // public String greet(String greeting) {
  // logger.info("wiadomość testowa dla " + greeting);
  //
  // String text = "[" + Instant.now() + "]: " + greeting;
  // return text;
  // }

  @MessageMapping("/sendMessage")
  public void sendPublicMessage(@Payload ChatMessage message, Principal user) {
    String toUser = "user2";
    // logger.info(sessionId);
    logger.info(message.getText());
    logger.info(user.getName());
    logger.info("wiadomość publiczna: " + message);
    chatMessageRepo.save(message);
    // return message;
    simpMessagingTemplate.convertAndSendToUser(
        toUser, "/secured/user/queue/specific-user", message);
  }
}