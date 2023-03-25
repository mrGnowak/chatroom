import "./App.css";
import "./components/chatbox/ChatContentStyle.css";
import Routes from "./routes/Routes";
import { UserProvider } from "./UserProvider";

function App() {
  return (
    <>
      <UserProvider>
        <Routes />
      </UserProvider>
    </>
  );
}

export default App;
