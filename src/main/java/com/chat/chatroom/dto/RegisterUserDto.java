package com.chat.chatroom.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    
    private String email;
    private String password;
    private String userName;
}