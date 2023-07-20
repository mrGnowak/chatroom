import { Layout } from "antd";
import "./App.css";
import { UserProvider } from "./UserProvider";
import { BrowserRouter } from "react-router-dom";
import AppRoutes from "./routes/AppRoutes";
import AppLayout from "./layout/AppLayout";
import ErrorBoundary from "./components/ErrorBoundary";

function App() {
  return (
    <>
      <ErrorBoundary>
        <UserProvider>
          <BrowserRouter>
            <AppLayout>
              <AppRoutes />
            </AppLayout>
          </BrowserRouter>
        </UserProvider>
      </ErrorBoundary>
    </>
  );
}

export default App;
