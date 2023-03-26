import React from "react";
import { Layout, theme } from "antd";
import "./layout.css";
import NavBar from "./NavBar";
import SigninContent from "../components/signup/SigninContent";
import { footerContent } from "../components/FooterContent";

const { Header, Content, Footer, Sider } = Layout;

const LayoutSignin: React.FC = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <Layout>
      <Header>
        <NavBar />
      </Header>
      <Content style={{ padding: "0 50px", marginTop: "50px" }}>
        <Layout style={{ padding: "24px 0", background: colorBgContainer }}>
          <Content
            style={{
              padding: "0 24px",
              minHeight: "700px",
              background: colorBgContainer,
            }}
          >
            <SigninContent />
          </Content>
        </Layout>
      </Content>
      <Footer style={{ textAlign: "center" }}>{footerContent}</Footer>
    </Layout>
  );
};

export default LayoutSignin;
