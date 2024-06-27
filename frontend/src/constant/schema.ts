
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
    id: number | undefined,
    roomId: number | undefined,
    senderId: number,
    receiverId: number | undefined,
    content: string,
    createdAt: string | undefined,
}