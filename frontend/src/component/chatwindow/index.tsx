import React, { useEffect, useState } from "react";
import ChatContent from "./ChatContent";
import ChatInput from "./chatinput";
import { MessageSchema, OnlineUserSchema } from "../../constant/schema";
import { useWebsockets } from "../../context/WebsocketsContext";

const ChatWindow = ({ selectedUser }: { selectedUser: OnlineUserSchema }) => {
    const { privateMessages } = useWebsockets();
    const [messages, setMessages] = useState<Array<MessageSchema>>([]);

    useEffect(() => {
        console.log("Use Effect - ChatWindow")
        setMessages(privateMessages.get(selectedUser.id) || []);
    }, [selectedUser, privateMessages])


    return (
        <div className="flex flex-col flex-auto h-full py-2 px-6">
            <div className="flex flex-col flex-auto flex-shrink-0 rounded-2xl bg-gray-100 h-full p-4" >
                <ChatContent selectedUser={selectedUser} messages={messages} />
                <ChatInput selectedUser={selectedUser} setMessages={setMessages} />
            </div>
        </div>
    )
}

export default ChatWindow;