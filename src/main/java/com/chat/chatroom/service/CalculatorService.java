package com.chat.chatroom.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.chatroom.ws.SocketTextHandler;

@Component
public class CalculatorService {

    private Integer number = 1;

    private SocketTextHandler socketTextHandler;

    @Autowired
    public CalculatorService(@Lazy SocketTextHandler socketTextHandler) {
        this.socketTextHandler = socketTextHandler;
    }

    public Integer getNumber() {
        return number;
    }

    public void addOne() {
        setNumber(number = number + 1);

    }

    public void setNumber(Integer number) {
        this.number = number;
        socketTextHandler.SendAll();
    }

}
