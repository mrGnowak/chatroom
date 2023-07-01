package com.chat.chatroom.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Rooms {

    @Id
    @GeneratedValue
    private Long roomId;
    @Column
    private String roomName;
    @Column
    private String roomStyle;

    @ManyToMany(mappedBy = "userRooms")
    Set<AppUser> usersInRoom;

}
