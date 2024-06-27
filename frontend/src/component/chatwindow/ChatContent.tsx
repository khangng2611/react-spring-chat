import { UserSchema, MessageSchema } from "../../constant/schema";
import { useSession } from "../../context/SessionContext";
import { useWebsockets } from "../../context/WebsocketsContext";
import FromMessage from "../message/FromMessage";
import ToMessage from "../message/ToMessage";
import React, { useEffect, useRef, useState } from 'react'

const ChatContent = ({ selectedUser }: { selectedUser: UserSchema }) => {
    const { details } = useSession();
    const { privateMessages, publicMessages } = useWebsockets();
    const [messages, setMessages] = useState<Array<MessageSchema>>([]);

    const messagesEndRef = useRef<HTMLDivElement>(null)
    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" })
    }
    useEffect(() => {
        if (selectedUser.id === -1)
            setMessages(publicMessages);
        else setMessages(privateMessages.get(selectedUser.id) || []);
        scrollToBottom();
    }, [selectedUser, privateMessages, publicMessages])

    return (
        <div className="flex flex-col h-full overflow-x-auto mb-4">
            <div className="flex flex-col h-full">
                <div className="grid grid-cols-12 gap-y-2">
                    {
                        messages.length ? messages.map((message: MessageSchema, index: number) => {
                            return (
                                message.sender.id === details?.id ?
                                    <FromMessage key={index} message={message} /> :
                                    <ToMessage key={index} isPublic={selectedUser.id === -1} message={message} />
                            )
                        }) : null
                    }
                    <div ref={messagesEndRef} />
                    {/* <div className="col-start-1 col-end-8 p-3 rounded-lg">
                        <div className="flex flex-row items-center">
                            <div
                                className="flex items-center justify-center h-10 w-10 rounded-full bg-indigo-500 flex-shrink-0"
                            >
                                A
                            </div>
                            <div
                                className="relative ml-3 text-sm bg-white py-2 px-4 shadow rounded-xl"
                            >
                                <div className="flex flex-row items-center">
                                    <button
                                        className="flex items-center justify-center bg-indigo-600 hover:bg-indigo-800 rounded-full h-8 w-10"
                                    >
                                        <svg
                                            className="w-6 h-6 text-white"
                                            fill="none"
                                            stroke="currentColor"
                                            viewBox="0 0 24 24"
                                            xmlns="http://www.w3.org/2000/svg"
                                        >
                                            <path
                                                strokeLinecap="round"
                                                strokeLinejoin="round"
                                                strokeWidth="1.5"
                                                d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"
                                            ></path>
                                            <path
                                                strokeLinecap="round"
                                                strokeLinejoin="round"
                                                strokeWidth="1.5"
                                                d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                                            ></path>
                                        </svg>
                                    </button>
                                    <div className="flex flex-row items-center space-x-px ml-4">
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-4 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-8 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-8 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-10 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-10 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-12 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-10 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-6 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-5 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-4 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-3 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-10 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-10 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-8 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-8 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-1 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-1 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-8 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-8 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-2 w-1 bg-gray-500 rounded-lg"></div>
                                        <div className="h-4 w-1 bg-gray-500 rounded-lg"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>  */}
                </div>
            </div>
        </div>
    )
}

export default ChatContent;
