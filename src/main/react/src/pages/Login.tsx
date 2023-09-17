import { Button, Form, Input } from "antd";
import Title from "antd/es/typography/Title";
import { useNavigate } from "react-router-dom";
import { useRefreshUser } from "../UserProvider";
import { LoginForm } from "../components/types/types";
//import { useRefreshUser } from '../UserProvider';

export default function Login() {
  const navigate = useNavigate();
  const refreshUser = useRefreshUser();

  const onFinish = async (values: LoginForm) => {
    try {
      await fetch("api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          userName: values.userName,
          password: values.password,
        }),
      });
      navigate("/");
      refreshUser();
    } catch (e) {
      console.log("Wrong pass", e);
    }
  };

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <>
      <Form
        name="basic"
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        style={{ maxWidth: 600 }}
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Title style={{ marginTop: "0px" }} level={3}>
          LOG IN
        </Title>
        <Form.Item
          label="Username"
          name="userName"
          rules={[{ required: true, message: "Please input your userName!" }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[{ required: true, message: "Please input your password!" }]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
          <Button type="primary" htmlType="submit">
            Submit
          </Button>
        </Form.Item>
      </Form>
    </>
  );
}
