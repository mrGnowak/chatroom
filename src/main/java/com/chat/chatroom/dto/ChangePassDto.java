package com.chat.chatroom.dto;

import lombok.Data;

@Data
public class ChangePassDto {
    private Long userId;
    private String password;
    private String newPassword;
}
