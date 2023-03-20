import { createBrowserRouter, RouterProvider } from "react-router-dom";

import React from "react";
import ErrorPage from "../components/error-page";
import HomeContent from "../components/home/HomeContent";
import LayoutHome from "../layout/LayoutHome";
import LayoutChat from "../layout/LayoutChat";
import LayoutUnauthorized from "../layout/LayoutUnauthorized";
import LayoutLogin from "../layout/LayoutLogin";
import LayoutSignin from "../layout/LayoutSignin";

export default function Routes() {
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

      {
        path: "/logout",
        //element: <Logout />,
        errorElement: <ErrorPage />,
      },
    ]);
  }, []);

  return <RouterProvider router={router} />;
}
