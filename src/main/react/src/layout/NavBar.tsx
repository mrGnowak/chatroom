import React, { useState } from "react";
import { AppstoreOutlined, SettingOutlined } from "@ant-design/icons";
import { MenuProps } from "antd";
import { Menu } from "antd";
//import { useUser } from '../UserProvider';

function NavBar() {
  //const user = useUser();
  const [current, setCurrent] = useState("mail");

  const items: MenuProps["items"] = React.useMemo(
    () => [
      {
        label: <a href="/">Home</a>,
        key: "home",
        icon: <AppstoreOutlined />,
      },
      {
        label: <a href="/chat">Chat</a>,
        key: "chat",
        icon: <SettingOutlined />,
      },
      {
        label: <a href="/login">Log in</a>,
        key: "login",
      },
      {
        label: <a href="/signup">Sign up</a>,
        key: "signin",
      },
    ],
    []
  );

  const onClick: MenuProps["onClick"] = (e) => {
    console.log("click ", e);
    setCurrent(e.key);
  };

  return (
    <>
      <div className="header">
        <div className="logo" />
        <Menu
          onClick={onClick}
          theme="dark"
          mode="horizontal"
          selectedKeys={[current]}
          items={items}
        />
      </div>
    </>
  );
}

export default NavBar;
