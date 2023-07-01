package com.chat.chatroom.stomp;

import java.text.SimpleDateFormat;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerWS {

    private static final Logger logger = LoggerFactory.getLogger(ControllerWS.class);

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public String greet(String greeting) {
        logger.info("wiadomość testowa dla " + greeting);

        String text = "[" + Instant.now() + "]: " + greeting;
        return text;
    }
}