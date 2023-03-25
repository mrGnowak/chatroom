export type Users = {
    id: number;
    userName: string;
    password: string;
    email: string;
  };
  
  export type ChatMessage = {
    id: number;
    text: string;
    toUser: number;
    senderUserId: number;
    senderUserName: string;
  }