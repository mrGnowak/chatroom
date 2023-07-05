package com.chat.chatroom.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.model.Rooms;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

    AppUser findByUserName(String userName);

    // @Query("SELECT r FROM Rooms r JOIN r.appUser u WHERE u.id = :userId GROUP BY
    // r HAVING COUNT(u) = 2")
    // List<Rooms> findRoomsByUserId(@Param("userId") Long userId);

    ArrayList<Rooms> findAllByUserId(Long userId);

}