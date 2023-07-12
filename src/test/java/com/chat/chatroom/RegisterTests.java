package com.chat.chatroom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import com.chat.chatroom.model.AppUser;
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
        var user1 = new AppUser();
        user1.setUserId(1L);
        user1.setUserName("user1");
        user1.setPassword("1234");
        user1.setEmail("user1@u.pl");

        AppUser user2 = new AppUser();
        user2.setUserId(2L);
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
        AppUser user3 = new AppUser();
        user3.setUserId(3L);
        user3.setUserName("user3");
        user3.setPassword("1234");
        user3.setEmail("user3@u.pl");

        assertEquals(userService.saveNewUser(user3), "Created!");
    }

    @Test
    @DirtiesContext
    void testEmailOrUsernameExist() {
        var user1 = new AppUser();
        user1.setUserId(1L);
        user1.setUserName("user1");
        user1.setPassword("1234");
        user1.setEmail("user1@u.pl");

        AppUser user2 = new AppUser();
        user2.setUserId(2L);
        user2.setUserName("user2");
        user2.setPassword("1234");
        user2.setEmail("user2@u.pl");

        userService.saveNewUser(user1);
        userService.saveNewUser(user2);
        userRepo.flush();

        var userWrongUserName = new AppUser();
        userWrongUserName.setUserId(1L);
        userWrongUserName.setUserName("user1");
        userWrongUserName.setPassword("1234");
        userWrongUserName.setEmail("user1234@u.pl");

        var userWrongEmail = new AppUser();
        userWrongEmail.setUserId(1L);
        userWrongEmail.setUserName("user1234");
        userWrongEmail.setPassword("1234");
        userWrongEmail.setEmail("user1@u.pl");

        var userWrongBoth = new AppUser();
        userWrongBoth.setUserId(1L);
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

    //
    void assertHashPassword(String password, String dbHashPassword) {
        var check = userService.checkPasswordMatches(password, dbHashPassword);
        assertEquals(check, true);
    }

}
