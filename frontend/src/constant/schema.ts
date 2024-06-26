
export interface UserDetailsSchema {
    id: number,
    username: string;
    fullName: string;
}

export interface OnlineUserSchema {
    id: number;
    username: string;
    fullName: string;
    status: "ONLINE" | "OFFLINE";
}

export interface MessageSchema {
    id: number,
    roomId: number,
    senderId: number,
    receiverId: number,
    content: string,
    createdAt: string,
}