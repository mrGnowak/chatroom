package com.chat.chatroom.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_rooms", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "userId") }, inverseJoinColumns = {
                    @JoinColumn(name = "room_id", referencedColumnName = "roomId") })
    @JsonManagedReference
    private Set<Rooms> userRooms = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AppUser other = (AppUser) obj;
        return Objects.equals(userId, other.userId);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                // Do not include userRooms in the toString method to avoid recursive call
                '}';
    }

}
