package com.chat.chatroom.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_rooms", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "room_id", referencedColumnName = "roomId", nullable = false, updatable = false) })
    private Set<Rooms> rooms = new HashSet<>();
}
