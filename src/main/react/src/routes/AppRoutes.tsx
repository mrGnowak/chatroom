import React from "react";

import { useUser } from "../UserProvider";
import Logout from "../layout/Logout";
import { Route, Routes } from "react-router-dom";
import Home from "../components/pages/Home";
import Login from "../components/pages/Login";
import Signin from "../components/pages/Signin";
import ChatContent from "../components/pages/chatbox/ChatContent";

export default function AppRoutes() {
  const user = useUser();
  console.log("🚀 ~ file: Routes.tsx:12 ~ Routes ~ user:", user);

  return (
    <Routes>
      {<Route path="/" element={<Home />} />}
      {<Route path="/login" element={<Login />} />}
      {<Route path="/logout" element={<Logout />} />}
      {<Route path="/signup" element={<Signin />} />}
      {<Route path="/chat" element={<ChatContent />} />}
      {/*<Route path="/about" element={<About />} />*/}
      {/*<Route path="/profile" element={<ProfileInfo />} />*/}
    </Routes>
  );

  //const router = React.useMemo(() => {
  //  return createBrowserRouter([
  //    {
  //      path: "/",
  //      element: <LayoutHome />,
  //      errorElement: <ErrorPage />,
  //    },
  //    {
  //      path: "/chat",
  //      element: <LayoutChat />,
  //      errorElement: <LayoutUnauthorized />,
  //    },
  //    ...(user == null
  //      ? [
  //          {
  //            path: "/login",
  //            element: <LayoutLogin />,
  //            errorElement: <ErrorPage />,
  //          },
  //          {
  //            path: "/signup",
  //            element: <LayoutSignin />,
  //            errorElement: <ErrorPage />,
  //          },
  //        ]
  //      : [
  //          {
  //            path: "/logout",
  //            element: <Logout />,
  //            errorElement: <ErrorPage />,
  //          },
  //          //{
  //          //  path: "/changepassword",
  //          //  element: <ChangePass />,
  //          //  errorElement: <ErrorPage />,
  //          //},
  //        ]),
  //  ]);
  //}, [user]);

  //return <RouterProvider router={router} />;
}
