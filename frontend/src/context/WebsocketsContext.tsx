import React, { createContext, useContext, useEffect, useRef, useState } from "react"
import Stomp, { Frame, Client } from 'stompjs';
import SockJS from 'sockjs-client';
import { useSession } from "./SessionContext"
import { MessageSchema, UserSchema } from '../constant/schema';
import { getAllMessages, getOnlineUsers } from "../services/api";

interface WebSocketContextSchema {
    onlineUsers: Map<number, UserSchema>;
    onDisconnected?: () => void;
    privateMessages: Map<number, Array<MessageSchema>>;
    sendPrivateMessage: (receiver: UserSchema, message: string) => void;
    publicMessages: Array<MessageSchema>;
    sendPublicMessage: (message: string) => void;
}

const WebSocketsContext = createContext<WebSocketContextSchema>({
    onlineUsers: new Map<number, UserSchema>(),
    onDisconnected: () => { },
    privateMessages: new Map<number, Array<MessageSchema>>(),
    sendPrivateMessage: () => { },
    publicMessages:  [],
    sendPublicMessage: () => { }
});

const WebsocketsContextProvider: React.FC<{ children: React.ReactNode }> = ({
    children,
}) => {
    const { details } = useSession();
    const wsClient = useRef<Client | null>(null);
    const [onlineUsers, setOnlineUsers] = useState(new Map());
    const [privateMessages, setPrivateMessages] = useState(new Map());
    const [publicMessages, setPublicMessages] = useState<Array<MessageSchema>>([]);

    useEffect(() => {
        if (details !== null && !wsClient.current) {
            connect(details)
        } // eslint-disable-next-line
    }, [details])

    async function connect(userDetails: UserSchema) {
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
        // listen new user queue
        wsClient.current.subscribe(
            '/online',
            onNewOnlineReceived
        );
        // listen private message queue
        wsClient.current.subscribe(
            `/user/${details?.id}/queue/messages`,
            onPrivateMessageReceived
        );
        // listen public message queue
        wsClient.current.subscribe(
            `/public`,
            onPublicMessageReceived
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

    const onNewOnlineReceived = async (message: Stomp.Message) => {
        const newUser: UserSchema = JSON.parse(message.body);
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

    const onPrivateMessageReceived = (message: Stomp.Message) => {
        const newMessage: MessageSchema = JSON.parse(message.body);
        const sender = onlineUsers.get(newMessage.sender.id);
        if (!sender) return;
        if (!privateMessages.get(sender.id))
            privateMessages.set(sender.id, [])
        privateMessages.get(sender.id).push(newMessage);
        setPrivateMessages(new Map(privateMessages));
    };

    const onPublicMessageReceived = (message: Stomp.Message) => {
        const newMessage: MessageSchema = JSON.parse(message.body);
        const sender = onlineUsers.get(newMessage.sender.id);
        if (!sender) return;
        setPublicMessages((prev: Array<MessageSchema>) => [...prev, newMessage]);
    };

    const sendPrivateMessage = async (receiver: UserSchema, message: string) => {
        if (!receiver) return;
        wsClient.current?.send(
            '/app/chat/private',
            {},
            JSON.stringify({
                sender: details!,
                receiver: receiver,
                content: message,
            })
        );
        const newMessage: MessageSchema = {
            sender: details!,
            receiver: receiver,
            content: message,
            id: undefined,
            roomId: undefined,
            createdAt: undefined,
        }
        const senderChat = onlineUsers.get(receiver.id);
        if (!senderChat) return;
        if (!privateMessages.get(receiver.id))
            privateMessages.set(receiver.id, [])
        privateMessages.get(receiver.id).push(newMessage);
        setPrivateMessages(new Map(privateMessages));
    }

    const sendPublicMessage = async (message: string) => {
        wsClient.current?.send(
            '/app/chat/public',
            {},
            JSON.stringify({
                sender: details!,
                content: message,
            })
        );
        const newMessage: MessageSchema = {
            sender: details!,
            content: message,
            receiver: undefined,
            id: undefined,
            roomId: undefined,
            createdAt: undefined,
        }
        setPublicMessages((prev: Array<MessageSchema>) => [...prev, newMessage]);
    }

    return (
        <WebSocketsContext.Provider value={{
            onlineUsers,
            onDisconnected,
            privateMessages,
            sendPrivateMessage,
            publicMessages,
            sendPublicMessage,
        }}>
            {children}
        </WebSocketsContext.Provider>
    )
}
export const useWebsockets = () => useContext(WebSocketsContext);

export default WebsocketsContextProvider;
