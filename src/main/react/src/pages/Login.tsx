import { Alert, Button, Form, Input, notification } from "antd";
import Title from "antd/es/typography/Title";
import { useNavigate } from "react-router-dom";
import { useRefreshUser } from "../UserProvider";
import { LoginForm } from "../components/types/types";

export default function Login() {
  const navigate = useNavigate();
  const refreshUser = useRefreshUser();

  const [wrongPass, wrongPassHolder] = notification.useNotification();

  const openNotificationWithIcon = () => {
    wrongPass["error"]({
      message: "Wrong username or password!",
      description:
        "Try again. If you have forgotten your password, use the password change option. ",
    });
  };

  const onFinish = async (values: LoginForm) => {
    try {
      const accept = await fetch("api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          userName: values.userName,
          password: values.password,
        }),
      });
      if (accept.status === 200) {
        refreshUser();
        navigate("/");
      } else {
        openNotificationWithIcon();
      }
    } catch (e) {
      console.log("Wrong pass", e);
    }
  };

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
    openNotificationWithIcon();
  };

  return (
    <>
      {wrongPassHolder}
      <Form
        name="basic"
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        style={{ maxWidth: 600, padding: "25px" }}
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
