import { useState } from "react";
import { Button, Checkbox, Form, Input } from "antd";
import Title from "antd/es/typography/Title";
import { ChangePassword, SignInForm } from "../components/types/types";
import { useUser } from "../UserProvider";

export default function ChangePass() {
  const [response, setResponse] = useState<string | undefined>();
  const user = useUser();

  const updatePass = (values: ChangePassword) => {
    const requestOptions = {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        userId: user?.id,
        password: values.password,
        newPassword: values.newPassword,
      }),
    };
    fetch("api/auth/changepass", requestOptions)
      .then((response) => response.text())
      .then((data) => {
        setResponse(data);
      });
  };

  const onFinish = (values: ChangePassword) => {
    console.log("Success:", values);
    updatePass(values);
  };

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <>
      <div style={{ padding: "25px" }}>
        <Title level={3}>Change password</Title>
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
          <Form.Item
            label="Current password"
            name="password"
            rules={[
              {
                required: true,
                message: "Please input your current password!",
              },
              { max: 20, message: "Please input your current password" },
            ]}
          >
            <Input.Password />
          </Form.Item>
          <Form.Item
            label="New password"
            name="newPassword"
            rules={[
              {
                required: true,
                message: "Please input your new password!",
              },
              {
                min: 4,
                message: "Wrong password! Password be at least 8 characters ",
              },
              //{
              //  pattern:
              //    /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!#$%\-_=+<>])([a-zA-Z0-9!#$%\-_=+<>]+)$/,
              //  message:
              //    "Password must contains: uppecrase letter, lovercase letter, any number 0 - 9, special characters (!#$%\\-_=+<>)",
              //},
            ]}
            hasFeedback
          >
            <Input.Password />
          </Form.Item>

          <Form.Item
            label="Confirm new password"
            name="confpassword"
            dependencies={["newPassword"]}
            hasFeedback
            rules={[
              {
                required: true,
                message: "Please confirm your password!",
              },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue("newPassword") === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(
                    new Error(
                      "The two passwords that you entered do not match!"
                    )
                  );
                },
              }),
            ]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
            <Button type="primary" htmlType="submit">
              Change password!
            </Button>
          </Form.Item>
          <Form wrapperCol={{ offset: 8, span: 16 }}>{response}</Form>
        </Form>
      </div>
    </>
  );
}
