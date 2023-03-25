import { Avatar, Input, Tooltip } from "antd";
import React from "react";
import { useUser } from "../../UserProvider";
import { ChatMessage, Users } from "../types";
import "./ChatContentStyle.css";
import { UserOutlined } from "@ant-design/icons";

export default function ChatContent() {
  const user = useUser();
  const [users, setUsers] = React.useState<Users[]>([]);
  const [messages, setMessages] = React.useState<ChatMessage[]>([]);
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
      const tempMessages = JSON.parse(evt.data.toString());
      console.log("tempMessages" + tempMessages);
      setMessages(tempMessages.reverse());
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

  const getUsers = () =>
    fetch("api/chat/users", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.json())
      .then((data) => {
        setUsers(data as Users[]);
      });

  React.useEffect(() => {
    getUsers();
  }, []);

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
        senderUserName: user?.userName,
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

  return (
    <>
      Serwer status: {connected ? "Połączono" : "Brak połączenia"}
      <div id="content-chat" style={{ height: "450px", overflow: "auto" }}>
        <div style={{ position: "sticky", bottom: 0 }}>
          <section>
            <div style={{ display: "flex", flexFlow: "column wrap" }}>
              {messages?.map((message) =>
                message?.senderUserId === user?.id ? (
                  <div key={message.id} className="sender-style content">
                    {message.text}
                  </div>
                ) : (
                  <div key={message.id} className="content">
                    {" "}
                    <Tooltip
                      placement="right"
                      title={
                        message.senderUserName ? message.senderUserName : " "
                      }
                    >
                      <div style={{ display: "inline" }}>
                        <Avatar
                          size="small"
                          icon={<UserOutlined />}
                          style={{ marginTop: "" }}
                        />
                      </div>

                      <div
                        className="getter-style"
                        style={{ display: "inline" }}
                      >
                        {message.text}
                      </div>
                    </Tooltip>
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
