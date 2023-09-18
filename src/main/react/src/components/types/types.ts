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
export type ChatMessage = {
  mesId: number;
  roomId: number;
  senderUserId: number;
  text: string;
};

export type Users = {
  userId: number;
  userName: string;
  password: string;
  email: string;
};
export type ChangePassword = {
  userId: number;
  password: string;
  newPassword: string;
}
  

