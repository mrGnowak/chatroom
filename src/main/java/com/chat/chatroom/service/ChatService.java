package com.chat.chatroom.service;

import org.springframework.context.annotation.Lazy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.chatroom.model.ChatMessage;
import com.chat.chatroom.repo.ChatMessageRepo;
import com.chat.chatroom.ws.SocketTextHandler;

@Component
public class ChatService {

    private Integer number = 2;

    private SocketTextHandler socketTextHandler;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    private List<ChatMessage> chatMessages;

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

    public List<ChatMessage> getMessages(Long toUserId, Long userId) {
        if (toUserId.equals(-1L)) {
            chatMessages = chatMessageRepo.findFirst100ByToUserOrderByIdDesc(toUserId);
        } else {
            chatMessages = chatMessageRepo.findFirst100ByToUserAndSenderUserIdOrToUserAndSenderUserIdOrderByIdDesc(
                    toUserId, userId,
                    userId, toUserId);
        }
        return chatMessages;
    }

}
