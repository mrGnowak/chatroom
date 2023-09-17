import React from "react";
import { Layout, Row, theme } from "antd";

import { Content, Footer, Header } from "antd/es/layout/layout";
import { footerContent } from "../components/types/FooterContent";
import NavBar from "./NavBar";
import { Divider } from "rc-menu";

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

        <Content>
          <div
            style={{
              minHeight: `calc(100vh - 131px)`,
              background: colorBgContainer,
              width: "50%",
              minWidth: "1000px",
              position: "relative",
              marginLeft: "auto",
              marginRight: "auto",
              padding: "30px",
            }}
          >
            {children}
          </div>
        </Content>

        <Footer style={{ textAlign: "center" }}>{footerContent}</Footer>
      </Layout>
    </>
  );
}
