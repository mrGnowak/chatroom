import { Stomp } from "@stomp/stompjs";
import React, { useState, useEffect } from "react";

export default function HomeContent() {
  const wsUrl = "ws://127.0.0.1:8080/websocket";
  const [message, setMessage] = useState("");
  const [connectedMessage, setConnectedMessage] = useState("No connection");
  const client = Stomp.client(wsUrl);
  client.configure({
    brokerURL: wsUrl,
    onConnect: () => {
      setConnectedMessage("connected");

      //client.subscribe("/queue/now", (message) => {
      //  console.log(message);
      //});

      client.subscribe("/topic/test", (message) => {
        setMessage(message.body);
      });
    },
  });
  client.activate();

  const sendMessage = () => {
    client.publish({ destination: "/app/test", body: "Hello world" });
  };

  return (
    <>
      <div>
        {connectedMessage}
        <button onClick={sendMessage}>Send message</button>
        {message}
      </div>
    </>
  );
}
