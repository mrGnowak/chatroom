package com.chat.chatroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.chatroom.dto.ChangePassDto;
import com.chat.chatroom.dto.RegisterUserDto;
import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder globalPasswordEncoder;

    public String saveNewUser(RegisterUserDto regUser) {
        if (!userRepo.existsByUserName(regUser.getUserName())) {
            if (!userRepo.existsByEmail(regUser.getEmail())) {
                String hashPass = globalPasswordEncoder.encode(regUser.getPassword());
                regUser.setPassword(hashPass);
                AppUser user = new AppUser();
                user.setEmail(regUser.getEmail());
                user.setPassword(hashPass);
                user.setUserName(regUser.getUserName());
                userRepo.save(user);

                System.out.println("Created!");
                return "Created!";
            } else {
                System.out.println("This email is already in use!");
                return "This email is already in use!";
            }
        } else {
            System.out.println("UserName is occupied");
            return "UserName is occupied!";
        }
    }

    public ResponseEntity<String> updateUser(ChangePassDto changePassDto) {
        AppUser newUser = userRepo.findById(changePassDto.getUserId()).get();
        if (checkPasswordMatches(changePassDto.getPassword(),
                userRepo.findById(changePassDto.getUserId()).get().getPassword())) {
            newUser.setPassword(globalPasswordEncoder.encode(changePassDto.getNewPassword()));
            userRepo.save(newUser);
            return ResponseEntity.ok("Changed!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password!");
        }
    }

    public Boolean checkUserExistEmail(AppUser user) {
        if (userRepo.findByEmail(user.getEmail()) == null) {
            return true;
        }
        return false;
    }

    public Boolean checkUserExistUserName(AppUser user) {
        if (userRepo.findByUserName(user.getUserName()) == null) {
            return true;
        }
        return false;
    }

    public boolean checkPasswordMatches(String password, String hashPassword) {
        boolean isPasswordMatches = globalPasswordEncoder.matches(password, hashPassword);
        return isPasswordMatches;
    }

    public Long returnLoggedUserId(String email, String password) {
        var user = userRepo.findByEmail(email);
        if (user == null) {
            return null;
        } else {
            var checkPass = checkPasswordMatches(password, user.getPassword());
            if (checkPass) {
                return user.getUserId();
            }
        }
        return null;

    }

    public AppUser getUserById(Long userId) {
        var user = userRepo.findById(userId);
        return user.isPresent() ? user.get() : null;
    }

    public ResponseEntity<Object> getAllUsers() {
        List<AppUser> appUsers = userRepo.findAll();
        if (appUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

}
