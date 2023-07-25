package com.chat.chatroom.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.model.Rooms;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

    AppUser findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    List<AppUser> findByUserNameContaining(String userName);

    @Query("SELECT u.userRooms FROM AppUser u JOIN u.userRooms r WHERE u.userId = :userId")
    Set<Rooms> findRoomsByUserId(@Param("userId") Long userId);

    @Query("SELECT u FROM AppUser u JOIN u.userRooms r WHERE r.roomId = :roomId")
    List<AppUser> findUsersByRoomId(@Param("roomId") Long roomId);

}