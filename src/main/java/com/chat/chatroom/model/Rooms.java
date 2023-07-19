package com.chat.chatroom.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "rooms")
public class Rooms {

    @Id
    @GeneratedValue
    private Long roomId;
    @Column
    private String roomName;
    @Column
    private String roomStyle;
    @Column
    private RoomPrivacyEnum privacy;

    @ManyToMany(mappedBy = "userRooms", fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnoreProperties("userRooms")
    private Set<AppUser> users = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Rooms other = (Rooms) obj;
        return Objects.equals(roomId, other.roomId);
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomStyle='" + roomStyle + '\'' +
                ", privacy=" + privacy +
                // Do not include users in the toString method to avoid recursive call
                '}';
    }
}
