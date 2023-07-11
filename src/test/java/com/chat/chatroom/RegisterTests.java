package com.chat.chatroom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.model.Rooms;
import com.chat.chatroom.repo.UserRepo;
import com.chat.chatroom.service.UserService;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = "java.com.chat.chatroom")

class RegisterTests {

    @Autowired
    private UserRepo userRepo;

    @MockBean
    private UserService userService;

    @Test
    void contextLoads() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @DirtiesContext
    void testRepoAdd() {
        AppUser user1 = new AppUser();
        user1.setUserId(1L);
        user1.setUserName("user1");
        user1.setPassword("1234");
        user1.setEmail("user1@u.pl");

        AppUser user2 = new AppUser();
        user1.setUserId(2L);
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
        // AppUser user3 = new AppUser();
        // user1.setUserId(3L);
        // user2.setUserName("user3");
        // user2.setPassword("1234");
        // user2.setEmail("user3@u.pl");
        //
        // assertEquals(userService.saveNewUser(user3), "Created!");
    }

    // @Test
    // @DirtiesContext
    // void testEmailOrUsernameExist() {
    // var user1 = new AppUser(1L, "user1", "1234", "user1@user.com");
    // var user2 = new AppUser(2L, "user2", "1234", "user2@user.com");
    //
    // userService.saveNewUser(user1);
    // userService.saveNewUser(user2);
    // usersRepo.flush();
    //
    // var userWrongUserName = new AppUser(1L, "user1", "1234",
    // "user1234@user.com");
    // var userWrongEmail = new AppUser(1L, "user1234", "1234", "user1@user.com");
    // var userWrongBoth = new AppUser(2L, "user2", "1234", "user2@user.com");
    //
    // // if username exist returnes statement:
    // // UserName is occupied!
    // var statementuserName = userService.saveNewUser(userWrongUserName);
    // assertEquals(statementuserName, "UserName is occupied!");
    //
    // // if email exist returned statement:
    // // This email is already in use!
    // var statementEmail = userService.saveNewUser(userWrongEmail);
    // assertEquals(statementEmail, "This email is already in use!");
    // // if both exist return only username statement:
    // // UserName is occupied!
    // var statementBoth = userService.saveNewUser(userWrongBoth);
    // assertEquals(statementBoth, "UserName is occupied!");
    // }
    //
    void assertHashPassword(String password, String dbHashPassword) {
        var check = userService.checkPasswordMatches(password, dbHashPassword);
        assertEquals(check, true);
    }

}
