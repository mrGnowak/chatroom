import React from "react";
import { useUser } from "../UserProvider";
import Logout from "../layout/Logout";
import { Route, Routes } from "react-router-dom";
import Home from "../pages/Home";
import Login from "../pages/Login";
import Signin from "../pages/Signin";
import ChatContent from "../pages/chatbox/ChatContent";

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
    </Routes>
  );
}
