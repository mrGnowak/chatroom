import { Input } from "antd";
import React from "react";
import { useUser } from "../../UserProvider";
import { ChatMessage, Users } from "../types";
import "./ChatContentStyle.css";

export default function ChatContent() {
  const user = useUser();
  const [number, setNumber] = React.useState("10");
  const [users, setUsers] = React.useState<Users[]>([]);
  const [messages, setMessages] = React.useState<ChatMessage[]>([]);
  //const [messages2, setMessages2] = React.useState<ChatMessage[]>([]);
  const [message, setMessage] = React.useState<string | undefined>();
  const onChange = (e: React.FormEvent<HTMLInputElement>) =>
    setMessage(e.currentTarget.value);

  const websocketRef = React.useRef<any>();
  const [connected, setConnected] = React.useState<boolean>(false);
  const bottomRef = React.useRef<any>(null);

  React.useEffect(() => {
    websocketRef.current = new WebSocket(`ws://localhost:8080/ws/messages`);
    const w = websocketRef.current;
    w.onmessage = (evt: any) => {
      console.log(evt);
      const temMessages = JSON.parse(evt.data.toString());
      console.log("temMessages" + temMessages);
      setMessages(temMessages);
    };
    w.onerror = (evt: any) => {
      console.error(evt);
    };
    w.onopen = (evt: any) => {
      setConnected(true);
    };
    w.onclose = (evt: any) => {
      setConnected(false);
    };
  }, []);

  React.useEffect(() => {
    fetch("api/chat/number", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.text())
      .then((data) => {
        setNumber(data);
      });
  }, []);

  const getUsers = () =>
    fetch("api/chat/users", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.json())
      .then((data) => {
        setUsers(data as Users[]);
      });

  function sendMessage() {
    if (user?.id === undefined || message === undefined || message === "") {
      return;
    }
    fetch("api/chat/send", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        text: message,
        toUser: -1,
        senderUserId: user?.id,
      }),
    })
      .then((response) => response.text)
      .then(() => setMessage(""))
      .then((data) => console.log(data));
  }

  React.useEffect(() => {
    // 👇️ scroll to bottom every time messages change
    bottomRef.current?.scrollIntoView({ behavior: "auto" });
  }, [messages]);

  React.useEffect(() => {
    getUsers();
  }, []);

  return (
    <>
      Serwer status: {connected ? "Połączono" : "Brak połączenia"}
      <div id="content-chat" style={{ height: "450px", overflow: "auto" }}>
        <div style={{ position: "sticky", bottom: 0 }}>
          <section>
            <div style={{ display: "flex", flexFlow: "column wrap" }}>
              {messages?.map((message) =>
                message?.senderUserId === user?.id ? (
                  <div key={message.id} className="sender-style">
                    {message.text} | sender: {message.senderUserId}
                  </div>
                ) : (
                  <div key={message.id} className="getter-style">
                    {message.text} | sender: {message.senderUserId}
                  </div>
                )
              )}
            </div>
            <div ref={bottomRef} />
          </section>
        </div>
      </div>
      <div style={{ marginTop: "30px", overflow: "auto" }}>
        <Input.Search
          enterButton="Send"
          size="large"
          onSearch={sendMessage}
          onChange={onChange}
          value={message}
        />
      </div>
    </>
  );
}
