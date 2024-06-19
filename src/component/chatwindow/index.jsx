import React from "react";
import ChatContent from "./ChatContent";
import ChatInput from "./ChatInput";

const ChatWindow = () => {
    const [messages, setMessages] = React.useState([
        {sender: 'atom', content: []}
    ]);
    const generateMessage = async (text) => {
        setMessages((prev) => prev.concat({ sender: 'me', content: text }));
        const { request, response } = {
            request : [],
            response : []
        }
        request && setMessages((prev) => prev.concat({ sender: 'atom', content: request }));
        setMessages((prev) => prev.concat({ sender: 'atom', content: response }));
    }
    return (
        <div className="flex flex-col flex-auto h-full py-2 px-6">
            <div className="flex flex-col flex-auto flex-shrink-0 rounded-2xl bg-gray-100 h-full p-4" >
                <ChatContent messages={messages} />
                <ChatInput generateMessage={generateMessage} />
            </div>
        </div>
    )
}

export default ChatWindow;