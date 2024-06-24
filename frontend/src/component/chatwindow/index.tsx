import React, { useEffect, useState } from "react";
import ChatContent from "./ChatContent";
import ChatInput from "./chatinput";
import { OnlineUserSchema, useSession } from "../../context/SessionContext";
import useFetch from "../../hook/useFetch";

export interface MessageSchema {
    id: number,
    roomId: number,
    senderId: number,
    receiverId: number,
    content: string,
    createdAt: string,
}

const ChatWindow = ({ selectedUser }: { selectedUser: OnlineUserSchema }) => {
    const { details } = useSession();
    const [messages, setMessages] = useState<Array<MessageSchema>>([]);
    const fetchedMessages = useFetch(`messages/${details?.id}/${selectedUser.id}`) as Array<MessageSchema>;
    useEffect(() => {
        if (fetchedMessages.length)
            setMessages(fetchedMessages);
    }, [fetchedMessages, selectedUser]);

    const setNewMessage = async (text: string) => {
        setMessages((prev: Array<MessageSchema>) => ([...prev, {
            id: 0,
            roomId: 0,
            senderId: details?.id || 0,
            receiverId: selectedUser.id || 0,
            content: text,
            createdAt: new Date().toISOString()
        }]))
    }
    return (
        <div className="flex flex-col flex-auto h-full py-2 px-6">
            <div className="flex flex-col flex-auto flex-shrink-0 rounded-2xl bg-gray-100 h-full p-4" >
                <ChatContent receiver={selectedUser} messages={messages} />
                <ChatInput receiver={selectedUser} setNewMessage={setNewMessage} />
            </div>
        </div>
    )
}

export default ChatWindow;