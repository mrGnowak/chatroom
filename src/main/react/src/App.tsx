import { Layout } from "antd";
import "./App.css";
import { UserProvider } from "./UserProvider";
import { BrowserRouter } from "react-router-dom";
import AppRoutes from "./routes/AppRoutes";
import AppLayout from "./layout/AppLayout";

function App() {
  return (
    <>
      <BrowserRouter>
        <UserProvider>
          <AppLayout>
            <AppRoutes />
          </AppLayout>
        </UserProvider>
      </BrowserRouter>
    </>
  );
}

export default App;
