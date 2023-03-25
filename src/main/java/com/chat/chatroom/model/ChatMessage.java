package com.chat.chatroom.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ChatMessage {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String text;
    @Column
    private Long senderUserId;
    @Column
    private Long toUser;
    @Column
    private String senderUserName;

}
