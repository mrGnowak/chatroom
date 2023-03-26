import React from "react";
import { Button, Result } from "antd";
import { Layout, theme } from "antd";
import "./layout.css";
import NavBar from "./NavBar";
import { footerContent } from "../components/FooterContent";

const { Header, Content, Footer, Sider } = Layout;

const LayoutUnauthorized: React.FC = () => {
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
          <Content style={{ padding: "0 24px", minHeight: "700px" }}>
            <Result
              status="403"
              title="403"
              subTitle="Sorry, you are not authorized to access this page."
              extra={
                <Button type="primary" href={"/login"}>
                  Log in
                </Button>
              }
            />
          </Content>
        </Layout>
      </Content>
      <Footer style={{ textAlign: "center" }}>{footerContent}</Footer>
    </Layout>
  );
};

export default LayoutUnauthorized;
