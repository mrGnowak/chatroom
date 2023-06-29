package com.chat.chatroom.stomp;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerWS {

    private static final Logger logger = LoggerFactory.getLogger(ControllerWS.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    //
    public ControllerWS(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/test")
    public void greet(String greeting) {
        logger.info("wiadomość testowa dla " + greeting);

        String text = "[" + Instant.now() + "]: " + greeting;
        this.simpMessagingTemplate.convertAndSend("/topic/test", text);
    }
}