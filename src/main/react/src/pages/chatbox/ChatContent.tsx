import {
  Affix,
  Avatar,
  Col,
  Input,
  Layout,
  List,
  Row,
  theme,
  Tooltip,
} from "antd";
import React from "react";
import { useUser } from "../../UserProvider";
import "./ChatContentStyle.css";
import { UserOutlined } from "@ant-design/icons";
import { Content } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import ChatBoxContent from "./ChatBoxContent";
import { ChatMessage, Room, Users } from "../../components/types/types";

export default function ChatContent() {
  const sessionUser = useUser();
  const [newMessage, setNewMessage] = React.useState<ChatMessage>();
  const [users, setUsers] = React.useState<Users[]>([]);
  const [rooms, setRooms] = React.useState<Room[]>([]);
  const websocketRef = React.useRef<any>();
  const [activeChatbox, setActiveChatbox] = React.useState<number>(-1);
  const [connected, setConnected] = React.useState<boolean>(false);
  var temp: string;

  const {
    token: { colorBgContainer },
  } = theme.useToken();

  //React.useEffect(() => {
  //  console.log(window.location.host);
  //  websocketRef.current = new WebSocket(
  //    `ws://${window.location.host}/ws/messages`
  //  );
  //  const w = websocketRef.current;
  //  w.onmessage = (evt: any) => {
  //    console.log(evt);
  //    //const tempMessages = JSON.parse(evt.data.toString());
  //    //console.log("tempMessages" + tempMessages);
  //    temp = evt.data.toString();
  //  };
  //  w.onerror = (evt: any) => {
  //    console.error(evt);
  //  };
  //  w.onopen = (evt: any) => {
  //    setConnected(true);
  //  };
  //  w.onclose = (evt: any) => {
  //    setConnected(false);
  //  };
  //}, []);

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
      <Row style={{ width: "850px" }}>
        <Col span={3}>
          <Sider
            style={{
              background: colorBgContainer,
              padding: "24px 24px",
              maxHeight: "700px",
              overflow: "auto",
              height: "700px",
            }}
            width={250}
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
          </Sider>
        </Col>
        <Col span={20} push={4}>
          <Content
            style={{
              padding: "24px 24px",
              height: "700px",
              background: colorBgContainer,
              marginLeft: "10px",
              maxWidth: "550px",
              width: "550px",
            }}
          >
            Serwer status: {connected ? "Połączono" : "Brak połączenia"}
            <ChatBoxContent roomId={activeChatbox} newMessage={newMessage} />
          </Content>
        </Col>
      </Row>
    </>
  );
}
