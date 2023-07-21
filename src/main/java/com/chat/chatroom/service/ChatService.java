package com.chat.chatroom.service;

import org.springframework.context.annotation.Lazy;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.chatroom.model.ChatMessage;
import com.chat.chatroom.repo.ChatMessageRepo;
import com.chat.chatroom.repo.UserRepo;
import com.chat.chatroom.ws.SocketTextHandler;

@Component
public class ChatService {

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

    public void setMessages(List<ChatMessage> chatMessages) {

        chatMessages = chatMessageRepo.findAll();
        socketTextHandler.SendAll();
    }

    public void save(ChatMessage chatMessage) {
        chatMessageRepo.save(chatMessage);
        // socketTextHandler.SendAll();
    }

    public List<ChatMessage> getMessages(Long roomId) {
        if (roomId.equals(null)) {
            return null;
        } else {
            chatMessages = chatMessageRepo.findFirst100ByRoomIdOrderByMesIdDesc(
                    roomId);
            return chatMessages;
        }
    }
}
