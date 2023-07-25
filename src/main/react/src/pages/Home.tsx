import { Stomp } from "@stomp/stompjs";
import React, { useState, useEffect } from "react";
import SockJS from "sockjs-client";

export default function Home() {
  const wsUrl = "ws://127.0.0.1:8080/websocket";
  const sockUrl = "http://localhost:8080/websocket";
  const [message, setMessage] = useState("");
  const [connectedMessage, setConnectedMessage] = useState("No connection");
  //const client = Stomp.client(wsUrl);
  const client = Stomp.over(new SockJS(sockUrl));
  client.configure({
    brokerURL: wsUrl,
    onConnect: () => {
      setConnectedMessage("Connected");

      //client.subscribe("/queue/now", (message) => {
      //  console.log(message);
      //});

      client.subscribe("/topic/test", (message: any) => {
        setMessage(message.body);
      });
    },
  });
  client.activate();

  const sendMessage = () => {
    client.publish({ destination: "/app/test", body: "Hello world" });
  };

  const getRooms = () => {
    fetch("api/chat/getUserRooms/4", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.json())
      .then((data) => {
        //setMessage(data as Message[]);
      });
  };

  const getUsersInRooms = () => {
    fetch("api/chat/getUsersInRoom/2", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.json())
      .then((data) => {
        //setMessage(data as Message[]);
      });
  };

  return (
    <>
      <div>
        {connectedMessage}
        <button onClick={sendMessage}>Send message</button>
        {message}
      </div>
      <div>
        <button onClick={getRooms}>getAllRoms</button>
        {message}

        <button onClick={getUsersInRooms}>getUsersInRooms</button>
      </div>
    </>
  );
}