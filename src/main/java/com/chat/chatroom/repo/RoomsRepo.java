package com.chat.chatroom.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatroom.model.Rooms;

public interface RoomsRepo extends JpaRepository<Rooms, Long> {

    List<Rooms> findByRoomNameContaining(String roomName);
}
