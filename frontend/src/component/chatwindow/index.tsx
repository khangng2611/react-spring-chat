import React, { useEffect, useState } from "react";
import ChatContent from "./ChatContent";
import ChatInput from "./chatinput";
import { OnlineUserSchema, useSession, MessageSchema } from "../../context/SessionContext";
import useFetch from "../../hook/useFetch";

const ChatWindow = ({ selectedUser }: { selectedUser: OnlineUserSchema }) => {
    const { details } = useSession();
    const { newMessages } = useSession();
    const [messages, setMessages] = useState<Array<MessageSchema>>([]);
    const fetchedMessages = useFetch(`messages/${details?.id}/${selectedUser?.id}`) as Array<MessageSchema>;

    useEffect(() => {
        const filterBySelectedUser = newMessages.filter(message => message.senderId === selectedUser.id)
        if (fetchedMessages.length) {
            const filterDuplicate = filterBySelectedUser.filter(message => !fetchedMessages.some(fetchedMessage => fetchedMessage.id === message.id))
            setMessages([...fetchedMessages, ...filterDuplicate]);
        } else setMessages(filterBySelectedUser);
    }, [newMessages, fetchedMessages, selectedUser.id]);

    return (
        <div className="flex flex-col flex-auto h-full py-2 px-6">
            <div className="flex flex-col flex-auto flex-shrink-0 rounded-2xl bg-gray-100 h-full p-4" >
                <ChatContent receiver={selectedUser} messages={messages} />
                <ChatInput receiver={selectedUser} setMessages={setMessages} />
            </div>
        </div>
    )
}

export default ChatWindow;