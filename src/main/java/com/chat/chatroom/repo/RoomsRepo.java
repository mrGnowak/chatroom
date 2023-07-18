package com.chat.chatroom.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chat.chatroom.model.Rooms;

public interface RoomsRepo extends JpaRepository<Rooms, Long> {
    boolean existsById(Long roomId);

    Rooms findByRoomId(Long roomId);
}
