import { createBrowserRouter, RouterProvider } from "react-router-dom";

import React from "react";
import ErrorPage from "../components/error-page";
import HomeContent from "../components/home/HomeContent";
import LayoutHome from "../layout/LayoutHome";
import LayoutChat from "../layout/LayoutChat";
import LayoutUnauthorized from "../layout/LayoutUnauthorized";
import LayoutLogin from "../layout/LayoutLogin";
import LayoutSignin from "../layout/LayoutSignin";
import { useUser } from "../UserProvider";
import Logout from "../layout/Logout";

export default function Routes() {
  const user = useUser();
  console.log("🚀 ~ file: Routes.tsx:12 ~ Routes ~ user:", user);

  const router = React.useMemo(() => {
    return createBrowserRouter([
      {
        path: "/",
        element: <LayoutHome />,
        errorElement: <ErrorPage />,
      },
      {
        path: "/chat",
        element: <LayoutChat />,
        errorElement: <LayoutUnauthorized />,
      },
      ...(user == null
        ? [
            {
              path: "/login",
              element: <LayoutLogin />,
              errorElement: <ErrorPage />,
            },
            {
              path: "/signup",
              element: <LayoutSignin />,
              errorElement: <ErrorPage />,
            },
          ]
        : [
            {
              path: "/logout",
              element: <Logout />,
              errorElement: <ErrorPage />,
            },
            //{
            //  path: "/changepassword",
            //  element: <ChangePass />,
            //  errorElement: <ErrorPage />,
            //},
          ]),
    ]);
  }, [user]);

  return <RouterProvider router={router} />;
}
