package com.chat.chatroom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.model.ChatMessage;
import com.chat.chatroom.repo.UserRepo;
import com.chat.chatroom.service.ChatService;
import com.chat.chatroom.service.RoomsService;

@RequestMapping(value = "/api/chat")
@RestController
public class ChatController {

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    public ChatService chatService;

    @Autowired
    public RoomsService roomsService;

    @GetMapping(value = "/users")
    public List<AppUser> users() {
        return userRepo.findAll();
    }

    @PostMapping(value = "/addone")
    public void addOne() {
        chatService.addOne();
    }

    @GetMapping(value = "/message/private/{userId}/{toUserId}")
    public List<ChatMessage> returnMessagesPrivate(@PathVariable Long userId, @PathVariable Long toUserId) {
        System.out.println("private messages " + userId + " | " + toUserId);
        return chatService.getMessages(toUserId, userId);

    }

    @GetMapping(value = "/number")
    public void returnNumber() {
        chatService.getNumber();
    }

    @PostMapping(value = "/send")
    public void addOne(@RequestBody ChatMessage chatMessage) {
        chatService.save(chatMessage);

    }

    @GetMapping(value = "/getRooms/{userId}")
    public void getAllUserRooms(@PathVariable Long userId) {
        roomsService.getAllUserRooms(userId);
    }
}
