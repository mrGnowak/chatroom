import { Avatar, Col, List, Row, theme } from "antd";
import React from "react";
import { useUser } from "../../UserProvider";
import "./ChatContentStyle.css";
import { UserOutlined } from "@ant-design/icons";
import { Content, Header } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import ChatBoxContent from "./ChatBoxContent";
import { ChatMessage, Room, Users } from "../../components/types/types";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export default function ChatContent() {
  const sessionUser = useUser();
  const [newMessage, setNewMessage] = React.useState<ChatMessage>();
  const [users, setUsers] = React.useState<Users[]>([]);
  const [rooms, setRooms] = React.useState<Room[]>([]);
  const [activeChatbox, setActiveChatbox] = React.useState<number>(-1);
  const [connected, setConnected] = React.useState<boolean>(false);
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  const sockUrl = "http://localhost:8080/secured/room";
  const client = Stomp.over(new SockJS(sockUrl));
  var sessionId = "";

  client.connect({}, function (frame: any) {
    var url = client.ws._transport.url;
    url = url.replace("ws://localhost:8080/secured/room/", "");
    url = url.replace("/websocket", "");
    url = url.replace(/^[0-9]+\//, "");
    console.log("Your current session is: " + url);
    sessionId = url;
    setConnected(true);
    client.subscribe(
      "/secured/user/queue/specific-user" + "-user" + sessionId,
      (message: any) => {
        setNewMessage(JSON.parse(message.body) as ChatMessage);
        console.log(message.body);
      }
    );
    client.subscribe("/topic/sendPublic", (message: any) => {
      setNewMessage(JSON.parse(message.body) as ChatMessage);
      console.log(message.body);
    });
  });
  client.activate();

  const getUsers = () =>
    fetch("api/chat/users", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.json())
      .then((data) => {
        setUsers(data as Users[]);
      });

  const getRooms = () =>
    fetch("api/chat/getUserRooms/" + sessionUser?.id, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.json())
      .then((data) => {
        setRooms(data as Room[]);
      });

  React.useEffect(() => {
    getUsers();
    getRooms();
  }, []);

  function handleClick(id: number) {
    setActiveChatbox(id);
  }
  return (
    <>
      <Row
        style={{
          maxWidth: "950px",
          margin: "30px",
        }}
      >
        <Col
          span={6}
          style={{
            background: colorBgContainer,
            //margin: "24px",
            padding: "24px 24px",
            maxHeight: "700px",
            overflow: "auto",
            height: "700px",
            width: "250px",
            backgroundColor: "#E2E2E2",
          }}
        >
          {" "}
          <h4>Rooms lists:</h4>
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
            dataSource={rooms}
            renderItem={(room, index) =>
              sessionUser ? (
                <List.Item>
                  <List.Item.Meta
                    avatar={
                      <Avatar
                        style={{ marginTop: "20px" }}
                        icon={<UserOutlined />}
                      />
                    }
                    title={
                      <a onClick={() => handleClick(room.roomId)}>
                        {room.roomName}
                      </a>
                    }
                    description="Zacznij rozmowe!"
                  />
                </List.Item>
              ) : null
            }
          />
        </Col>

        <Col span={18}>
          <Content
            style={{
              padding: "24px 24px",
              height: "700px",
              background: colorBgContainer,
              backgroundColor: "#EFEFEF",
            }}
          >
            <ChatBoxContent
              roomId={activeChatbox}
              newMessage={
                newMessage?.roomId == activeChatbox ? newMessage : undefined
              }
              client={client}
            />
            Server status: {connected ? "Connected " : "Disconected"}
            Nowa wiadomość: {newMessage?.text}
          </Content>
        </Col>
      </Row>
    </>
  );
}
