import React, { useState, useEffect } from "react";
import Stomp from "stompjs";

interface Message {
  body: string;
  headers: any;
}

const HomeContent: React.FC = () => {
  const [stompClient, setStompClient] = useState<any>(null);
  const [subscription, setSubscription] = useState<any>(null);
  const [messages, setMessages] = useState<Message[]>([]);

  useEffect(() => {
    const socket = new WebSocket("ws://localhost:8080/websocket");
    const stompClient = Stomp.over(socket);
    setStompClient(stompClient);
  }, []);

  useEffect(() => {
    if (stompClient) {
      stompClient.connect({}, () => {
        console.log("Connected to STOMP server");
      });
    }
    return () => {
      if (subscription) {
        subscription.unsubscribe();
      }
      if (stompClient) {
        stompClient.disconnect(() => {
          console.log("Disconnected from STOMP server");
        });
      }
    };
  }, [stompClient, subscription]);

  const subscribeToDestination = () => {
    const subscription = stompClient.subscribe(
      "/topic/greetings",
      (message: Message) => {
        console.log(`Received message: ${message.body}`);
        setMessages((prevMessages) => [...prevMessages, message]);
      }
    );
    setSubscription(subscription);
  };

  const sendMessage = () => {
    stompClient.send("/hello", {}, "Hello, STOMP!");
  };

  return (
    <div>
      <button onClick={subscribeToDestination}>Subscribe</button>
      <button onClick={sendMessage}>Send message</button>
      <ul>
        {messages.map((message, index) => (
          <li key={index}>
            <pre>{message.body}</pre>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default HomeContent;
