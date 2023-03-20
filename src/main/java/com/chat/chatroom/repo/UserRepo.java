package com.chat.chatroom.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatroom.model.AppUser;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

    AppUser findByUserName(String userName);
}