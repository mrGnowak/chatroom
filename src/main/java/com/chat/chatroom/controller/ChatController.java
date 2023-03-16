package com.chat.chatroom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.repo.UserRepo;
import com.chat.chatroom.service.CalculatorService;

@RequestMapping(value = "/api")
@RestController
public class ChatController {

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    public CalculatorService calculatorService;

    @GetMapping(value = "/users")
    public List<AppUser> users() {
        return userRepo.findAll();
    }

    @PostMapping(value = "/addone")
    public void addOne() {
        calculatorService.addOne();
    }

    @GetMapping(value = "/number")
    public Integer returnNumber() {
        return calculatorService.getNumber();
    }
}
