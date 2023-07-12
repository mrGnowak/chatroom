package com.chat.chatroom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.chatroom.model.Rooms;
import com.chat.chatroom.repo.RoomsRepo;
import com.chat.chatroom.repo.UserRepo;

@Service
public class RoomsService {
    @Autowired
    private RoomsRepo roomsRepo;

    @Autowired
    private UserRepo userRepo;

    private Rooms rooms;

    Logger logger = LoggerFactory.getLogger(RoomsService.class);

    public boolean checkIsPMRoomExist(Long userId, Long toUserId) {

        return false;

    }

    public void createNewUsersRoom(Rooms rooms) {

        roomsRepo.save(rooms);

    }

}
