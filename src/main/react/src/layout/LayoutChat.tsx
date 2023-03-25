import React from "react";
import { Layout, theme } from "antd";
import "./layout.css";
import ChatContent from "../components/chatbox/ChatContent";
import NavBar from "./NavBar";
import UserContent from "../components/users/UserContent";
import { footerContent } from "../components/FooterContent";

const { Header, Content, Footer, Sider } = Layout;

const LayoutChat: React.FC = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <Layout>
      <Header>
        <NavBar />
      </Header>
      <Content style={{ padding: "0 50px", marginTop: "50px" }}>
        <Layout style={{ justifyContent: "center" }}>
          <Sider
            style={{
              background: colorBgContainer,
              padding: "24px 24px",
              height: "600px",
            }}
            width={250}
          >
            <UserContent />
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
            <ChatContent />
          </Content>
        </Layout>
      </Content>
      <Footer style={{ textAlign: "center", marginTop: "150px" }}>
        {footerContent}
      </Footer>
    </Layout>
  );
};

export default LayoutChat;
