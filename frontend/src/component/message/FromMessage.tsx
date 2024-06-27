import React from "react";
import { useSession } from "../../context/SessionContext";
import { AVATAR_COLORS } from "../../constant/color";
import { MessageSchema } from "../../constant/schema";

const FromMessage = ({ message }: { message: MessageSchema }) => {
    const { details } = useSession();
    const [showTimestamp, setShowTimestamp] = React.useState(false);
    const formattedTime = message.createdAt?.slice(0, message.createdAt?.lastIndexOf("."));

    return (
        <div className="col-start-3 col-end-13 px-3 rounded-lg" onMouseEnter={() => setShowTimestamp(true)} onMouseLeave={() => setShowTimestamp(false)}>
            <div className="flex items-end justify-start flex-row-reverse">
                <div
                    className={`flex items-center justify-center h-10 w-10 rounded-full flex-shrink-0 ${AVATAR_COLORS[details!.id % AVATAR_COLORS.length]}`}
                >
                    {details?.fullName[0].toUpperCase()}
                </div>
                <div className="relative mr-3 text-sm bg-blue-100 py-2 px-4 shadow rounded-xl min-w-fit max-w-max" >
                    <p className="break-words" style={{ whiteSpace: "pre-wrap", wordBreak: "break-word" }}>
                        {message.content}
                    </p>
                </div>
                <p className="mr-2 text-xs text-gray-400 italic">{showTimestamp ? formattedTime : formattedTime?.split(" ")[1].slice(0, 5)}</p>
            </div>
        </div>
    )
}

export default FromMessage;