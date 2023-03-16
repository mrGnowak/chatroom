package com.chat.chatroom.ws;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.chat.chatroom.service.CalculatorService;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(SocketTextHandler.class);

    @Autowired
    public CalculatorService calculatorService;

    CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        logger.info("wykonano handler ");

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        var path = session.getUri().getPath();
        logger.info("ws connection established: " + path);
        sessions.add(session);
        sendToClient(session, currentStatus());

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.warn("ws transport error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("ws connection closed: " + status.getReason());
        sessions.remove(session);
    }

    private TextMessage currentStatus() {
        return new TextMessage(calculatorService.getNumber().toString());
    }

    private void sendToClient(WebSocketSession client, TextMessage message) {
        try {
            client.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendAll() {
        var message = currentStatus();
        for (var client : sessions) {
            sendToClient(client, message);
        }
    }
}
