import React from "react";
import "./App.css";

type Users = {
  id: number;
  username: string;
};

function App() {
  const [number, setNumber] = React.useState("10");
  const [users, setUsers] = React.useState<Users[]>([]);

  const websocketRef = React.useRef<any>();
  const [connected, setConnected] = React.useState<boolean>(false);

  React.useEffect(() => {
    websocketRef.current = new WebSocket(`ws://localhost:8080/ws/number`);
    const w = websocketRef.current;
    w.onmessage = (evt: any) => {
      setNumber(evt.data);
    };
    w.onerror = (evt: any) => {
      console.error(evt);
    };
    w.onopen = (evt: any) => {
      setConnected(true);
    };
    w.onclose = (evt: any) => {
      setConnected(false);
    };
  }, []);

  React.useEffect(() => {
    fetch("api/number", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.text())
      .then((data) => {
        setNumber(data);
      });
  }, []);

  const addOne = () =>
    fetch("api/addone", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.text)
      .then((data) => console.log(data));

  const getUsers = () =>
    fetch("api/users", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    })
      .then((response) => response.json())
      .then((data) => {
        setUsers(data as Users[]);
      });

  React.useEffect(() => {
    getUsers();
  }, []);

  return (
    <>
      <button onClick={addOne}>dodaj jeden</button>
      <div>Hellooo : {number} </div>
      <div>users: </div>
      <div></div>
      {users.map((user) => (
        <div key={user.id}>
          {user.id} : {user.username}
        </div>
      ))}
      <div>Serwer status: {connected ? "Połączono" : "Brak połączenia"}</div>
    </>
  );
}

export default App;
