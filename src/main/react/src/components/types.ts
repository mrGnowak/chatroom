export type Users = {
    userId: number;
    userName: string;
    password: string;
    email: string;
  };
  
  export type ChatMessage = {
    mesId: number;
    text: string;
    senderUserId: number;
    roomId: number;
  }

  export type Room = {
    roomId: number;
    roomName: string;
    roomStyle: string;
  }