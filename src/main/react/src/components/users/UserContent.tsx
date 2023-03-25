import { Avatar, List } from "antd";
import React from "react";
import { Users } from "../types";
import { UserOutlined } from "@ant-design/icons";
import { useUser } from "../../UserProvider";

export default function UserContent() {
  const sessionUser = useUser();
  const [users, setUsers] = React.useState<Users[]>([]);

  const getUsers = () =>
    fetch("api/chat/users", {
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
    <>
      <h4>User lists:</h4>
      <List itemLayout="horizontal">
        <List.Item>
          <List.Item.Meta
            avatar={<Avatar style={{ marginTop: "20px" }} />}
            title={<a href="/chat">Public room</a>}
            description="Zacznij rozmowe!"
          />
        </List.Item>
      </List>
      <List
        itemLayout="horizontal"
        dataSource={users}
        renderItem={(user, index) =>
          user.id !== sessionUser?.id ? (
            <List.Item>
              <List.Item.Meta
                avatar={
                  <Avatar
                    style={{ marginTop: "20px" }}
                    icon={<UserOutlined />}
                  />
                }
                title={
                  <a href="/">
                    {user.userName}| ID: {user.id}
                  </a>
                }
                description="Zacznij rozmowe!"
              />
            </List.Item>
          ) : null
        }
      />
    </>
  );
}
