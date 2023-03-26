import React from "react";
import { Layout, theme } from "antd";
import "./layout.css";
import NavBar from "./NavBar";
import { footerContent } from "../components/FooterContent";
import ChatContent from "../components/chatbox/ChatContent";

const { Header, Content, Footer, Sider } = Layout;

const LayoutChat: React.FC = () => {
  return (
    <Layout>
      <Header>
        <NavBar />
      </Header>
      <Content style={{ padding: "0 50px", marginTop: "50px" }}>
        <ChatContent />
      </Content>
      <Footer style={{ textAlign: "center", marginTop: "150px" }}>
        {footerContent}
      </Footer>
    </Layout>
  );
};

export default LayoutChat;
