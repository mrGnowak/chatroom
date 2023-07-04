package com.chat.chatroom.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chat.chatroom.model.Rooms;

public interface RoomsRepo extends JpaRepository<Rooms, Long> {

    // @Query("SELECT r FROM Rooms r JOIN r.appUser u GROUP BY r HAVING COUNT(u) =
    // 2")
    // List<Rooms> findRoomsForTwoPeople();
}
