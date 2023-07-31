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

import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.model.ChatMessage;
import com.chat.chatroom.repo.ChatMessageRepo;
import com.chat.chatroom.repo.UserRepo;

@Controller
public class ChatControllerStomp {

  private static final Logger logger = LoggerFactory.getLogger(ChatControllerStomp.class);

  @Autowired
  private ChatMessageRepo chatMessageRepo;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/sendPublic")
  @SendTo("/topic/sendPublic")
  public ChatMessage sendPublicMessage(ChatMessage message) {
    chatMessageRepo.save(message);
    return message;
  }

  @MessageMapping("/sendMessage")
  public void sendPublicMessage(ChatMessage message, Principal user, @Header("simpSessionId") String sessionId) {
    var roomId = message.getRoomId();
    var users = userRepo.findUsersByRoomId(roomId);
    String sendToUserName = null;
    chatMessageRepo.save(message);
    for (AppUser u : users) {
      sendToUserName = u.getUserName();
      simpMessagingTemplate.convertAndSendToUser(
          sendToUserName, "/secured/user/queue/specific-user", message);
    }
  }
}