import Avatar from "antd/es/avatar";
import Tooltip from "antd/es/tooltip";
import { UserOutlined } from "@ant-design/icons";
import Input from "antd/es/input";
import React from "react";
import { useUser } from "../../UserProvider";
import { Button } from "antd";
import { ChatMessage } from "../../components/types/types";

type Props = {
  roomId: number;
  newMessage: ChatMessage | undefined;
};

export default function ChatBoxContent({ roomId, newMessage }: Props) {
  const [message, setMessage] = React.useState<string | undefined>();
  const [messages, setMessages] = React.useState<ChatMessage[]>([]);
  const bottomRef = React.useRef<any>(null);
  const sessionUser = useUser();

  const onChange = (e: React.FormEvent<HTMLInputElement>) =>
    setMessage(e.currentTarget.value);

  const currentUser = sessionUser ? sessionUser.id : undefined;
  //messages for room
  const getMessages = () =>
    fetch("api/chat/message/private/" + roomId, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.json())
      .then((data) => {
        setMessages(data.reverse() as ChatMessage[]);
      });

  React.useEffect(() => {
    getMessages();
  }, [roomId]);

  function sendMessage() {
    if (
      sessionUser?.id === undefined ||
      message === undefined ||
      message === ""
    ) {
      return;
    }
    fetch("api/chat/send", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        text: message,
        senderUserId: sessionUser?.id,
        roomId: roomId,
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
    newMessage ? setMessages([...messages, newMessage]) : undefined;
  }, [messages, newMessage]);

  return (
    <>
      <div id="content-chat" style={{ height: "530px", overflow: "auto" }}>
        <div style={{ position: "sticky", bottom: 0 }}>
          <section>
            <div style={{ display: "flex", flexFlow: "column wrap" }}>
              {messages?.map((message) =>
                message?.senderUserId === sessionUser?.id ? (
                  <div key={message.mesId} className="sender-style content">
                    {message.text}
                  </div>
                ) : (
                  <div key={message.mesId} className="content">
                    {" "}
                    <Tooltip
                      placement="right"
                      title={message.senderUserId ? message.senderUserId : " "}
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
      <div>
        testowa wiadomosć {roomId}{" "}
        <Button onClick={getMessages}>Refresh</Button>
      </div>
    </>
  );
}
