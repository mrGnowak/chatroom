package com.chat.chatroom.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatroom.model.ChatMessage;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

    ChatMessage findBySenderUserId(String senderUserId);

    List<ChatMessage> findFirst100ByRoomIdAndSenderUserIdOrderByIdDesc(Long roomId,
            Long senderUserId);

    List<ChatMessage> findFirst100ByRoomIdOrderByIdDesc(Long roomId);
}