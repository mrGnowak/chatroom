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

    @GetMapping(value = "/users")
    public List<AppUser> users() {
        return userRepo.findAll();
    }

    @GetMapping(value = "/message/private/{roomId}")
    public List<ChatMessage> returnMessagesPrivate(@PathVariable Long roomId) {
        return chatService.getMessages(roomId);
    }

    @PostMapping(value = "/send")
    public void addOne(@RequestBody ChatMessage chatMessage) {
        chatService.save(chatMessage);
    }

    @GetMapping(value = "/getUserRooms/{userId}")
    public Set<Rooms> findUserRooms(@PathVariable Long userId) {
        return userRepo.findRoomsByUserId(userId);

    }

    @GetMapping(value = "/getUsersInRoom/{roomId}")
    public List<AppUser> findusersInRoom(@PathVariable Long roomId) {
        return userRepo.findUsersByRoomId(roomId);
    }

    @PostMapping(value = "/joinUserToRoom/{userId}/{roomId}")
    public void joinUserToRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        var room = roomsRepo.findById(roomId).get();
        var user = userRepo.findById(userId).get();
        user.getUserRooms().add(room);
        room.getUsers().add(user);
        userRepo.save(user);
        roomsRepo.save(room);
    }

    @PostMapping(value = "/room/updateRoom")
    public void updateRoom(@RequestBody Rooms room) {
        roomsRepo.save(room);
    }
}
