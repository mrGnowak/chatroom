package com.chat.chatroom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.chat.chatroom.dto.RegisterUserDto;
import com.chat.chatroom.model.RoomPrivacyEnum;
import com.chat.chatroom.model.Rooms;
import com.chat.chatroom.repo.RoomsRepo;
import com.chat.chatroom.repo.UserRepo;
import com.chat.chatroom.service.RoomsService;
import com.chat.chatroom.service.UserService;

@SpringBootTest
@AutoConfigureTestDatabase

class CreateRoomsTests {

    @Autowired
    private RoomsRepo roomsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @DirtiesContext
    void createRooms() {

        RegisterUserDto user1 = new RegisterUserDto();
        user1.setUserName("user1");
        user1.setPassword("1234");
        user1.setEmail("user1@u.pl");

        userService.saveNewUser(user1);
        userRepo.flush();

        assertEquals(userRepo.findById(1L).get().getUserName(), "user1");

        var room1 = new Rooms();
        room1.setRoomId(1L);
        room1.setRoomName("Room no.1 - public");
        room1.setPrivacy(RoomPrivacyEnum.PUBLIC);

        var room2 = new Rooms();
        room2.setRoomId(2L);
        room2.setRoomName("Room no.2 - private");
        room2.setPrivacy(RoomPrivacyEnum.PRIVATE);

        var room3 = new Rooms();
        room3.setRoomId(3L);
        room3.setRoomName("Room no.3 - group");
        room3.setPrivacy(RoomPrivacyEnum.GROUP);

        roomsService.createNewUsersRoom(room1);
        roomsService.createNewUsersRoom(room2);
        roomsService.createNewUsersRoom(room3);

        assertEquals(roomsRepo.findById(1L).get().getRoomName(), "Room no.1 - public");
        assertEquals(roomsRepo.findById(1L).get().getPrivacy(), RoomPrivacyEnum.PUBLIC);
        assertEquals(roomsRepo.findById(2L).get().getPrivacy(), RoomPrivacyEnum.PRIVATE);
        assertEquals(roomsRepo.findById(3L).get().getPrivacy(), RoomPrivacyEnum.GROUP);
        assertEquals(roomsRepo.existsById(2L), true);

    }

    @Test
    public void testAddCreatedRoomToCreatdUser() {
        RegisterUserDto user1 = new RegisterUserDto();
        user1.setUserName("user1");
        user1.setPassword("1234");
        user1.setEmail("user1@u.pl");

        userService.saveNewUser(user1);
        userRepo.flush();

        var room1 = new Rooms();
        room1.setRoomId(1L);
        room1.setRoomName("Room no.1 - public");
        room1.setPrivacy(RoomPrivacyEnum.PUBLIC);

        roomsService.createNewUsersRoom(room1);

    }

}
