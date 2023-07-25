package com.chat.chatroom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.chat.chatroom.dto.RegisterUserDto;
import com.chat.chatroom.repo.UserRepo;
import com.chat.chatroom.service.UserService;

@SpringBootTest
@AutoConfigureTestDatabase

class RegisterTests {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @DirtiesContext
    void testRepoAdd() {
        RegisterUserDto user1 = new RegisterUserDto();
        user1.setUserName("user1");
        user1.setPassword("1234");
        user1.setEmail("user1@u.pl");

        RegisterUserDto user2 = new RegisterUserDto();
        user2.setUserName("user2");
        user2.setPassword("1234");
        user2.setEmail("user2@u.pl");

        userService.saveNewUser(user1);
        userService.saveNewUser(user2);
        userRepo.flush();

        assertEquals(userRepo.findById(1L).get().getUserName(), "user1");
        assertEquals(userRepo.findById(2L).get().getEmail(), "user2@u.pl");
        assertHashPassword("1234", userRepo.findById(1L).get().getPassword());

        // check correct statement:
        // except: Created!
        RegisterUserDto user3 = new RegisterUserDto();
        user3.setUserName("user3");
        user3.setPassword("1234");
        user3.setEmail("user3@u.pl");

        assertEquals(userService.saveNewUser(user3), "Created!");
    }

    @Test
    @DirtiesContext
    void testEmailOrUsernameExist() {
        RegisterUserDto user1 = new RegisterUserDto();
        user1.setUserName("user1");
        user1.setPassword("1234");
        user1.setEmail("user1@u.pl");

        RegisterUserDto user2 = new RegisterUserDto();
        user2.setUserName("user2");
        user2.setPassword("1234");
        user2.setEmail("user2@u.pl");

        userService.saveNewUser(user1);
        userService.saveNewUser(user2);
        userRepo.flush();

        RegisterUserDto userWrongUserName = new RegisterUserDto();
        userWrongUserName.setUserName("user1");
        userWrongUserName.setPassword("1234");
        userWrongUserName.setEmail("user1234@u.pl");

        RegisterUserDto userWrongEmail = new RegisterUserDto();
        userWrongEmail.setUserName("user1234");
        userWrongEmail.setPassword("1234");
        userWrongEmail.setEmail("user1@u.pl");

        RegisterUserDto userWrongBoth = new RegisterUserDto();
        userWrongBoth.setUserName("user2");
        userWrongBoth.setPassword("1234");
        userWrongBoth.setEmail("user2@u.pl");

        // if username exist returnes statement:
        // UserName is occupied!
        var statementuserName = userService.saveNewUser(userWrongUserName);
        assertEquals(statementuserName, "UserName is occupied!");

        // if email exist returned statement:
        // This email is already in use!
        var statementEmail = userService.saveNewUser(userWrongEmail);
        assertEquals(statementEmail, "This email is already in use!");
        // if both exist return only username statement:
        // UserName is occupied!
        var statementBoth = userService.saveNewUser(userWrongBoth);
        assertEquals(statementBoth, "UserName is occupied!");
    }

    void assertHashPassword(String password, String dbHashPassword) {
        var check = userService.checkPasswordMatches(password, dbHashPassword);
        assertEquals(check, true);
    }

}
