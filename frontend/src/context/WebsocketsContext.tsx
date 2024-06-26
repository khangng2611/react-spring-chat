import React, { createContext, useContext, useEffect, useRef, useState } from "react"
import Stomp, { Frame, Client } from 'stompjs';
import SockJS from 'sockjs-client';
import { useSession } from "./SessionContext"
import { MessageSchema, OnlineUserSchema, UserDetailsSchema } from '../constant/schema';
import { getAllMessages, getOnlineUsers } from "../services/api";

interface WebSocketContextSchema {
    onlineUsers: Map<number, Array<OnlineUserSchema>>;
    privateMessages: Map<number, Array<MessageSchema>>;
    onDisconnected?: () => void;
    sendMessage: (receiverId: number, message: string) => void;
}

const WebSocketsContext = createContext<WebSocketContextSchema>({
    onlineUsers: new Map<number, Array<OnlineUserSchema>>(),
    privateMessages: new Map<number, Array<MessageSchema>>(),
    onDisconnected: () => { },
    sendMessage: () => { }
});

const WebsocketsContextProvider: React.FC<{ children: React.ReactNode }> = ({
    children,
}) => {
    const { details } = useSession();
    const wsClient = useRef<Client | null>(null);
    const [onlineUsers, setOnlineUsers] = useState(new Map());
    const [privateMessages, setPrivateMessages] = useState(new Map());

    useEffect(() => {
        if (details !== null && !wsClient.current) {
            connect(details)
        } // eslint-disable-next-line
    }, [details])

    async function connect(userDetails: UserDetailsSchema) {
        const socket = new SockJS(`${process.env.REACT_APP_BE_URL}/ws` || '');
        wsClient.current = Stomp.over(socket);
        wsClient.current.connect({}, onConnected, onError);
        let fetchedOnlineUsers = await getOnlineUsers();
        for (const user of fetchedOnlineUsers) {
            if (user.id === userDetails.id) continue;
            const messages = await getAllMessages(userDetails.id, user.id);

            onlineUsers.set(user.id, user);
            setOnlineUsers(new Map(onlineUsers));

            privateMessages.set(user.id, messages);
            setPrivateMessages(new Map(privateMessages));
        }
    }

    const onConnected = () => {
        if (!wsClient.current) return;
        while (!wsClient.current.connected) { }
        wsClient.current.subscribe(
            '/online',
            onNewOnlineReceived
        );
        wsClient.current.subscribe(
            `/user/${details?.id}/queue/messages`,
            onMessageReceived
        );
        wsClient.current.send(
            '/app/user.addUser',
            {},
            JSON.stringify({
                id: details?.id,
                username: details?.username,
                fullName: details?.fullName,
            })
        );
    };

    const onError = (error: string | Frame) => {
        console.log('Error: ', error);
    };

    const onNewOnlineReceived = async (message: Stomp.Message) => {
        const newUser: OnlineUserSchema = JSON.parse(message.body);
        if (newUser.id === details?.id) return
        // check if a OFFLINE message is sent
        if (newUser.status === 'OFFLINE') {
            onlineUsers.delete(newUser.id);
            setOnlineUsers(new Map(onlineUsers));
            return
        }
        const existingUser = onlineUsers.get(newUser.id);
        if (existingUser) {
            // User already exists, update their information
            existingUser.username = newUser.username;
            existingUser.fullName = newUser.fullName;
            existingUser.status = newUser.status;
            onlineUsers.set(newUser.id, existingUser);
            setOnlineUsers(new Map(onlineUsers));
            return;
        }
        const messages = await getAllMessages(details?.id!, newUser.id);
        privateMessages.set(newUser.id, messages);
        setPrivateMessages(new Map(privateMessages));

        onlineUsers.set(newUser.id, newUser);
        setOnlineUsers(new Map(onlineUsers));
    };

    const onMessageReceived = (message: Stomp.Message) => {
        const newMessage: MessageSchema = JSON.parse(message.body);
        const sender = onlineUsers.get(newMessage.senderId);
        if (!sender) return;
        if (!privateMessages.get(sender.id))
            privateMessages.set(sender.id, [])
        privateMessages.get(sender.id).push(newMessage);
        setPrivateMessages(new Map(privateMessages));
    };

    const onDisconnected = () => {
        wsClient.current?.send(
            '/app/user.disconnectUser',
            {},
            JSON.stringify({
                id: details?.id,
                username: details?.username,
                fullName: details?.fullName,
            })
        );
    };

    const sendMessage = async (receiverId: number, message: string) => {
        if (!receiverId) return;
        wsClient.current?.send(
            '/app/chat',
            {},
            JSON.stringify({
                senderId: details?.id,
                receiverId: receiverId,
                content: message,
            })
        );
        const newMessage: MessageSchema = {
            id: 0,
            roomId: 0,
            senderId: details?.id || 0,
            receiverId: receiverId,
            content: message,
            createdAt: new Date().toISOString()
        }
        const senderChat = onlineUsers.get(receiverId);
        if (!senderChat) return;
        if (!privateMessages.get(receiverId))
            privateMessages.set(receiverId, [])
        privateMessages.get(receiverId).push(newMessage);
        setPrivateMessages(new Map(privateMessages));
    }

    return (
        <WebSocketsContext.Provider value={{
            onlineUsers,
            privateMessages,
            onDisconnected,
            sendMessage,
        }}>
            {children}
        </WebSocketsContext.Provider>
    )
}
export const useWebsockets = () => useContext(WebSocketsContext);

export default WebsocketsContextProvider;
