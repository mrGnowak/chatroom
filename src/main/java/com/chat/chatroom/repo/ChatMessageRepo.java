package com.chat.chatroom.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatroom.model.ChatMessage;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

    ChatMessage findBySenderUserId(String senderUserId);

    List<ChatMessage> findAllByToUser(Long toUser);

    List<ChatMessage> findFirst100ByToUserOrderByIdDesc(Long toUser);

    List<ChatMessage> findFirst100ByToUserAndSenderUserIdOrToUserAndSenderUserIdOrderByIdDesc(Long toUser1,
            Long senderUserId1,
            Long toUser2, Long senderUserId2);

    ChatMessage findByToUser(String toUser);
}