package com.chat.chatroom.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.chatroom.controller.AuthController;
import com.chat.chatroom.model.ChatMessage;
import com.chat.chatroom.model.Rooms;
import com.chat.chatroom.repo.ChatMessageRepo;
import com.chat.chatroom.repo.UserRepo;
import com.chat.chatroom.ws.SocketTextHandler;

import jakarta.annotation.PostConstruct;

@Component
public class ChatService {

    private Integer number = 2;

    private SocketTextHandler socketTextHandler;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private UserRepo userRepo;

    private List<ChatMessage> chatMessages;

    Logger logger = LoggerFactory.getLogger(ChatService.class);

    public ChatService(@Lazy SocketTextHandler socketTextHandler) {
        this.socketTextHandler = socketTextHandler;
    }

    public Integer getNumber() {
        return number;
    }

    public void addOne() {
        setNumber(number = number + 1);

    }

    public void setNumber(Integer number) {
        this.number = number;
        socketTextHandler.SendAll();
    }

    public void setMessages(List<ChatMessage> chatMessages) {

        chatMessages = chatMessageRepo.findAll();
        socketTextHandler.SendAll();
    }

    public void save(ChatMessage chatMessage) {
        chatMessageRepo.save(chatMessage);
        socketTextHandler.SendAll();
    }

    public List<ChatMessage> getMessages(Long roomId, Long userId) {
        // if (roomId.equals(null)) {
        // chatMessages = chatMessageRepo.findFirst100ByToUserOrderByIdDesc(userId);
        // } else {
        chatMessages = chatMessageRepo.findFirst100ByRoomIdAndSenderUserIdOrderByIdDesc(
                roomId, userId);
        // }
        return chatMessages;
    }

    // ******** */
    // @PostConstruct
    // public List<Rooms> getAllRooms() {
    // logger.info(userRepo.findAllByUserId(1L).toString());
    // return userRepo.findAllByUserId(1L);
    //
    // }
}
