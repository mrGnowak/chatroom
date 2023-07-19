export type SignInForm = {
    userName: string;
    email: string;
    password: string;
  };

  export type LoginForm = {
    userName: string;
    password: string;
  };

  export type Room = {
    roomId: number;
    roomName: string;
    roomStyle: string;
  };

  export type Message = {
    mesId: number;
    roomId: number;
    senderUserId: string;
    text: string;
  };
  