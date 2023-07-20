import React, { useState } from "react";
import { AppstoreOutlined, SettingOutlined } from "@ant-design/icons";
import { MenuProps } from "antd";
import { Menu } from "antd";
import { useUser } from "../UserProvider";

export default function NavBar() {
  const user = useUser();
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
      ...(user == null
        ? [
            {
              label: <a href="/login">Log in</a>,
              key: "login",
            },
            {
              label: <a href="/signup">Sign up</a>,
              key: "signin",
            },
          ]
        : [
            {
              label: (
                <span>
                  Hello <b>{user.userName}</b>
                </span>
              ),
              key: "user_options",
              icon: <SettingOutlined />,
              children: [
                {
                  label: <a href="/changepassword">Change password</a>,
                  key: "change_pass",
                },
                {
                  label: <a href="/logout">Logout</a>,
                  key: "logout",
                },
              ],
            },
          ]),
    ],
    [user]
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
