import React from "react";
import { Layout, Row, theme } from "antd";
import "./layout.css";
import { Content, Footer, Header } from "antd/es/layout/layout";
import { footerContent } from "../components/types/FooterContent";
import NavBar from "./NavBar";

export default function AppLayout({ children }: React.PropsWithChildren) {
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  return (
    <>
      <Layout>
        <Header>
          <NavBar />
        </Header>

        <Content
          style={{
            minHeight: "800px",
            background: colorBgContainer,
            width: "100%",
            position: "relative",
          }}
        >
          <Content
            style={{
              paddingLeft: "60px",
              paddingRight: "60px",
              paddingTop: "30px",
              paddingBottom: "30px",
            }}
          >
            {children}
          </Content>
        </Content>

        <Footer style={{ textAlign: "center" }}>{footerContent}</Footer>
      </Layout>
    </>
  );
}
