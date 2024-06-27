import React from "react";
import { MessageSchema } from "../../constant/schema";
import { AVATAR_COLORS } from "../../constant/color";
import { useWebsockets } from "../../context/WebsocketsContext";

const ToMessage = ({ message }: { message: MessageSchema }) => {
    const { onlineUsers } = useWebsockets();

    return (
        <div className="col-start-1 col-end-10 px-3 rounded-lg">
            <div className="flex flex-row items-end">
                <div className={`flex items-center justify-center h-10 w-10 rounded-full ${AVATAR_COLORS.at(message.sender.id % AVATAR_COLORS.length)} flex-shrink-0 text-sm overflow-hidden`} >
                    {onlineUsers.get(message.sender.id)?.fullName[0].toUpperCase()}
                </div>
                <div className="relative ml-3 text-sm bg-white py-2 px-4 shadow rounded-xl min-w-fit max-w-max" >
                    <p className="break-words" style={{ whiteSpace: "pre-wrap", wordBreak: "break-word" }}>
                        {message.content}
                    </p>
                </div>
            </div>
        </div>
    )
}

export default ToMessage;