import React from "react";
import { useSession } from "../../context/SessionContext";
import { AVATAR_COLORS } from "../../constant/color";

const FromMessage = ({ content }: { content: string }) => {
    const { details } = useSession();
    return (
        <div className="col-start-6 col-end-13 px-3 rounded-lg">
            <div className="flex items-center justify-start flex-row-reverse">
                <div
                    className={`flex items-center justify-center h-10 w-10 rounded-full flex-shrink-0 ${AVATAR_COLORS[details!.id % AVATAR_COLORS.length]}`}
                >
                    {details?.fullName[0].toUpperCase()}
                </div>
                <div className="relative mr-3 text-sm bg-blue-100 py-2 px-4 shadow rounded-xl min-w-fit max-w-max" >
                    <p className="break-words" style={{ whiteSpace: "pre-wrap", wordBreak: "break-word" }}>
                        {content}
                    </p>
                </div>
            </div>
        </div>
    )
}

export default FromMessage;