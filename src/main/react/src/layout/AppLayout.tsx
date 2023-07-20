import React from "react";
import { Layout, theme } from "antd";
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
        <Content style={{ padding: "0 50px", marginTop: "50px" }}>
          <Layout>
            <Content
              style={{
                padding: "0 24px",
                minHeight: "700px",
                background: colorBgContainer,
              }}
            >
              {children}
            </Content>
          </Layout>
        </Content>
        <Footer style={{ textAlign: "center" }}>{footerContent}</Footer>
      </Layout>
    </>
  );
}
