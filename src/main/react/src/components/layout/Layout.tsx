import React from "react";
import {
  LaptopOutlined,
  NotificationOutlined,
  UserOutlined,
} from "@ant-design/icons";
import type { MenuProps } from "antd";
import { Breadcrumb, Layout, Menu, theme } from "antd";
import "./layout.css";
import { Users } from "../types";

const { Header, Content, Footer, Sider } = Layout;

const items1: MenuProps["items"] = ["Home", "Login", "SignUp"].map((key) => ({
  key,
  label: `${key}`,
}));

const App: React.FC = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  const [number, setNumber] = React.useState("10");
  const [users, setUsers] = React.useState<Users[]>([]);

  const websocketRef = React.useRef<any>();
  const [connected, setConnected] = React.useState<boolean>(false);

  React.useEffect(() => {
    websocketRef.current = new WebSocket(`ws://localhost:8080/ws/number`);
    const w = websocketRef.current;
    w.onmessage = (evt: any) => {
      setNumber(evt.data);
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
    fetch("api/number", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.text())
      .then((data) => {
        setNumber(data);
      });
  }, []);

  const addOne = () =>
    fetch("api/addone", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.text)
      .then((data) => console.log(data));

  const getUsers = () =>
    fetch("api/users", {
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

  return (
    <Layout>
      <Header className="header">
        <div className="logo" />
        <Menu
          theme="dark"
          mode="horizontal"
          defaultSelectedKeys={["2"]}
          items={items1}
        />
      </Header>
      <Content style={{ padding: "0 50px", marginTop: "50px" }}>
        <Layout style={{ padding: "24px 0", background: colorBgContainer }}>
          <Sider style={{ background: colorBgContainer }} width={200}>
            <div style={{ padding: "0 24px" }}>user list</div>
            <Menu
              mode="inline"
              defaultSelectedKeys={["1"]}
              defaultOpenKeys={["sub1"]}
              style={{ height: "100%" }}
            />
          </Sider>
          <Content style={{ padding: "0 24px", minHeight: "700px" }}>
            <div>
              <button onClick={addOne}>dodaj jeden</button>
              <div>Hellooo : {number} </div>
              <div>users: </div>
              <div></div>
              {users.map((user) => (
                <div key={user.id}>
                  {user.id} : {user.username}
                </div>
              ))}
              <div>
                Serwer status: {connected ? "Połączono" : "Brak połączenia"}
              </div>
            </div>
          </Content>
        </Layout>
      </Content>
      <Footer style={{ textAlign: "center" }}>footer</Footer>
    </Layout>
  );
};

export default App;
