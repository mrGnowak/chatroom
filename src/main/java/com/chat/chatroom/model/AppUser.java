package com.chat.chatroom.model;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class AppUser {

    @Id
    @GeneratedValue
    private Long userId;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String email;

    @ManyToMany
    @JoinTable(name = "users_rooms", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Rooms> userRooms;
}
