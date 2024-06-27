import React from "react";
import { UserSchema } from "../../../constant/schema";
import SendBtn from "./SendBtn";
import AttachBtn from "./AttachBtn";
import IconBtn from "./IconBtn";
import { useWebsockets } from "../../../context/WebsocketsContext";

const ChatInput = ({ selectedUser }: { selectedUser: UserSchema }) => {
    const { sendPrivateMessage, sendPublicMessage } = useWebsockets();
    const [inputContent, setInputContent] = React.useState('');
    const handleInputChange = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setInputContent(e.target.value);
    };

    const handleOnClick = () => {
        if (!inputContent || !inputContent.trim()) return;
        if (selectedUser.id === -1)
            sendPublicMessage(inputContent);
        else sendPrivateMessage(selectedUser, inputContent);
        setInputContent('');
    }

    return (
        <div className="flex flex-row items-center h-16 rounded-xl bg-white w-full px-4">
            <AttachBtn />
            <div className="flex-grow ml-4">
                <div className="relative w-full">
                    <input
                        type="text"
                        className="flex w-full border rounded-xl focus:outline-none focus:border-blue-400 pl-4 h-10"
                        value={inputContent}
                        maxLength={1000}
                        onChange={handleInputChange}
                        onKeyDown={(e) => {
                            if (e.key === 'Enter') {
                                handleOnClick();
                            }
                        }}
                    />
                    <IconBtn />
                </div>
            </div>
            <SendBtn handleOnCLick={handleOnClick} />
        </div>
    )
}

export default ChatInput;