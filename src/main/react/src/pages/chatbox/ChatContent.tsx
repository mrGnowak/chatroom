import { Avatar, Col, Input, List, Modal, Row, theme } from "antd";
import React from "react";
import { useUser } from "../../UserProvider";
import "./ChatContentStyle.css";
import { PlusSquareOutlined, UserOutlined } from "@ant-design/icons";
import { Content, Header } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import ChatBoxContent from "./ChatBoxContent";
import { ChatMessage, Room, Users } from "../../components/types/types";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

interface FieldData {
  id: number;
  value: string;
}
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

  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [addRoomName, setAddRoomName] = React.useState<string | undefined>();
  const [addRoomUser, setAddRoomUser] = React.useState<string | undefined>();
  //const handleRoomNameChange = (event: React.FormEvent<HTMLInputElement>) => {
  //  setAddRoomName(event.currentTarget.value);
  //};
  //const handleUserChange = (event: React.FormEvent<HTMLInputElement>) => {
  //  setAddRoomUser(event.currentTarget.value);
  //};

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    setIsModalOpen(false);
    console.log(addRoomName + "   " + addRoomUser);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <Row style={{ width: "100%" }}>
        <Col
          span={6}
          style={{
            background: colorBgContainer,
            padding: "24px 24px",
            overflow: "auto",
            height: "calc(100vh - 131px)",
            width: "100%",

            backgroundColor: "#E2E2E2",
          }}
        >
          <h4>
            <Row>
              <Col flex="auto" style={{ margin: "15px" }}>
                Rooms lists:
              </Col>
              <Col flex="15px" style={{ margin: "15px" }}>
                <PlusSquareOutlined onClick={showModal} rev={undefined} />
              </Col>
            </Row>
          </h4>
          <Modal
            title="Create room"
            open={isModalOpen}
            onOk={handleOk}
            onCancel={handleCancel}
          >
            <p>Room name</p>
            <Input size="large" value={addRoomName} />
            <p>Invite user</p>
            <Input size="large" />
          </Modal>
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
                        icon={<UserOutlined rev={undefined} />}
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
          <div
            style={{
              padding: "24px 24px",
              height: "calc(100vh - 131px)",
              width: "100%",
              background: colorBgContainer,
              backgroundColor: "#EFEFEF",
            }}
          >
            <h4 style={{ margin: "5px" }}>
              {rooms?.map((room) =>
                activeChatbox === room.roomId ? room.roomName : ""
              )}
              {activeChatbox === -1 ? "Public room" : ""}
            </h4>
            <ChatBoxContent
              roomId={activeChatbox}
              newMessage={
                newMessage?.roomId == activeChatbox ? newMessage : undefined
              }
              client={client}
            />
            Server status: {connected ? "Connected " : "Disconected"}
            Nowa wiadomość: {newMessage?.text}
          </div>
        </Col>
      </Row>
    </>
  );
}
