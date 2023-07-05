package com.chat.chatroom.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.chatroom.model.AppUser;
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

    public void createNewPMRoom(Long userId, Long toUserId) {
        if (checkIsPMRoomExist(userId, toUserId)) {
            return;
        }

        rooms.setRoomName("pokojTestowy");
        rooms.setRoomStyle(null);
        roomsRepo.save(rooms);

        var user = userRepo.findById(userId);

        // Collection<AppUser> col;
        // col.add(userRepo.findById(userId));
        // col.add(userRepo.findById(toUserId));
        // rooms.setUsersInRoom();
    }

    public void createNewUsersRoom(Long userId, Long toUserId) {

    }

    public void getAllUserRooms(Long userId) {

        // logger.info(userRepo.findAllByUserId(userId).toString());
        // userRepo.findAllByUserId(userId);
    }

}
