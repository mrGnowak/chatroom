package com.chat.chatroom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.model.ChatMessage;
import com.chat.chatroom.repo.ChatMessageRepo;
import com.chat.chatroom.repo.UserRepo;
import com.chat.chatroom.service.ChatService;

@RequestMapping(value = "/api/chat")
@RestController
public class ChatController {

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    public ChatService chatService;

    @GetMapping(value = "/users")
    public List<AppUser> users() {
        return userRepo.findAll();
    }

    @PostMapping(value = "/addone")
    public void addOne() {
        chatService.addOne();
    }

    @GetMapping(value = "/messages")
    public List<ChatMessage> returnMessages() {
        return chatMessageRepo.findAll();
    }

    @GetMapping(value = "/number")
    public void returnNumber() {
        chatService.getNumber();
    }

    @PostMapping(value = "/send")
    public void addOne(@RequestBody ChatMessage chatMessage) {
        chatService.save(chatMessage);

    }
}
