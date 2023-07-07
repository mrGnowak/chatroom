package com.chat.chatroom.controller;

import java.util.List;
import java.util.Set;

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
import com.chat.chatroom.model.Rooms;
import com.chat.chatroom.repo.RoomsRepo;
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

    @Autowired
    public RoomsRepo roomsRepo;

    // private Rooms rooms;

    @GetMapping(value = "/users")
    public List<AppUser> users() {
        return userRepo.findAll();
    }

    @GetMapping(value = "/message/private/{userId}/{roomId}")
    public List<ChatMessage> returnMessagesPrivate(@PathVariable Long userId, @PathVariable Long roomId) {
        System.out.println("private messages " + userId + " | " + roomId);
        return chatService.getMessages(roomId, userId);

    }

    @PostMapping(value = "/send")
    public void addOne(@RequestBody ChatMessage chatMessage) {
        chatService.save(chatMessage);

    }

    @GetMapping(value = "/getUserRooms/{userId}")
    public Set<Rooms> findUserRooms(@PathVariable Long userId) {

        // System.out.println("User 1 rooms " +
        // userRepo.findById(userId).get().getUserRooms());
        // return userRepo.findById(userId).get().getUserRooms();
        System.out.println(userRepo.findRoomsByUserId(userId));

        return userRepo.findRoomsByUserId(userId);
        // findUsersByRoomId
    }

    @GetMapping(value = "/getUsersInRoom/{roomId}")
    public List<AppUser> findusersInRoom(@PathVariable Long roomId) {

        // System.out.println("Users in room 1 " +
        // roomsRepo.findById(roomId).get().getUsers());
        // return roomsRepo.findById(roomId).get().getUsers();
        System.out.println(userRepo.findUsersByRoomId(roomId));
        return userRepo.findUsersByRoomId(roomId);
    }
}
