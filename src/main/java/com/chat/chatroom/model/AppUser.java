package com.chat.chatroom.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_rooms", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "userId") }, inverseJoinColumns = {
                    @JoinColumn(name = "room_id", referencedColumnName = "roomId") })
    private Set<Rooms> userRooms = new HashSet<Rooms>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Rooms> getUserRooms() {
        return userRooms;
    }

    public void setUserRooms(Set<Rooms> userRooms) {
        this.userRooms = userRooms;
    }
}
