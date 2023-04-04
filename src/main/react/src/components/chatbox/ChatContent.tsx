import { Avatar, Input, Layout, List, theme, Tooltip } from "antd";
import React from "react";
import { useUser } from "../../UserProvider";
import { ChatMessage, Users } from "../types";
import "./ChatContentStyle.css";
import { UserOutlined } from "@ant-design/icons";
import { Content } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import ChatBoxContent from "./ChatBoxContent";

export default function ChatContent() {
  const sessionUser = useUser();
  const [newMessage, setNewMessage] = React.useState<ChatMessage>();
  const [users, setUsers] = React.useState<Users[]>([]);
  const websocketRef = React.useRef<any>();
  const [activeChatbox, setActiveChatbox] = React.useState<number>(-1);
  const [connected, setConnected] = React.useState<boolean>(false);
  var temp: string;
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  React.useEffect(() => {
    websocketRef.current = new WebSocket(
      `ws://${window.location.host}/ws/messages`
    );
    const w = websocketRef.current;
    w.onmessage = (evt: any) => {
      console.log(evt);
      //const tempMessages = JSON.parse(evt.data.toString());
      //console.log("tempMessages" + tempMessages);
      temp = evt.data.toString();
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

  function handleClick(id: number) {
    setActiveChatbox(id);
  }

  return (
    <>
      <Layout style={{ justifyContent: "center" }}>
        <Sider
          style={{
            background: colorBgContainer,
            padding: "24px 24px",
            height: "600px",
          }}
          width={250}
        >
          <h4>User lists:</h4>
          <List itemLayout="horizontal">
            <List.Item>
              <List.Item.Meta
                avatar={<Avatar style={{ marginTop: "20px" }} />}
                title={<a onClick={() => handleClick(-1)}>Public room</a>}
                description="Zacznij rozmowe!"
              />
            </List.Item>
          </List>
          <List
            itemLayout="horizontal"
            dataSource={users}
            renderItem={(user, index) =>
              user.id !== sessionUser?.id ? (
                <List.Item>
                  <List.Item.Meta
                    avatar={
                      <Avatar
                        style={{ marginTop: "20px" }}
                        icon={<UserOutlined />}
                      />
                    }
                    title={
                      <a onClick={() => handleClick(user.id)}>
                        {user.userName}
                      </a>
                    }
                    description="Zacznij rozmowe!"
                  />
                </List.Item>
              ) : null
            }
          />
        </Sider>
        <Content
          style={{
            padding: "24px 24px",
            height: "600px",
            background: colorBgContainer,
            marginLeft: "10px",
            maxWidth: "500px",
          }}
        >
          Serwer status: {connected ? "Połączono" : "Brak połączenia"}
          <ChatBoxContent toUserId={activeChatbox} newMessage={newMessage} />
        </Content>
      </Layout>
    </>
  );
}
