import Avatar from "antd/es/avatar";
import Tooltip from "antd/es/tooltip";
import { UserOutlined } from "@ant-design/icons";
import Input from "antd/es/input";
import React from "react";
import { ChatMessage } from "../types";
import { useUser } from "../../UserProvider";

type Props = {
  toUserId: number;
  messages: ChatMessage[];
};

export default function ChatBoxContent({ toUserId, messages }: Props) {
  const [message, setMessage] = React.useState<string | undefined>();
  const bottomRef = React.useRef<any>(null);
  const sessionUser = useUser();

  const onChange = (e: React.FormEvent<HTMLInputElement>) =>
    setMessage(e.currentTarget.value);

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
        toUser: -1,
        senderUserId: sessionUser?.id,
        senderUserName: sessionUser?.userName,
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
      <div id="content-chat" style={{ height: "450px", overflow: "auto" }}>
        <div style={{ position: "sticky", bottom: 0 }}>
          <section>
            <div style={{ display: "flex", flexFlow: "column wrap" }}>
              {messages?.map((message) =>
                message?.senderUserId === sessionUser?.id ? (
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
      <div>testowa wiadomosć {toUserId}</div>
    </>
  );
}
