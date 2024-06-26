import React from "react";
import { OnlineUserSchema } from "../../constant/schema";
import { AVATAR_COLORS } from "../../constant/color";

const ToMessage = ({ receiver, content }: { receiver: OnlineUserSchema, content: string }) => {
    return (
        <div className="col-start-1 col-end-10 px-3 py-1 rounded-lg">
            <div className="flex flex-row items-end">
                <div className={`flex items-center justify-center h-10 w-10 rounded-full ${AVATAR_COLORS.at(receiver.id%AVATAR_COLORS.length)} flex-shrink-0 text-sm overflow-hidden`} >
                    {receiver.fullName[0].toUpperCase()}
                </div>
                <div className="relative ml-3 text-sm bg-white py-2 px-4 shadow rounded-xl min-w-fit max-w-full" >
                    <p className="break-words" style={{ whiteSpace: "pre-wrap" }}>
                        {content}
                        {/* {
                            content && content.map((line, id) => (
                                <span key={id} >
                                    <span dangerouslySetInnerHTML={{ __html: line }}></span>
                                </span>
                            ))
                        } */}
                    </p>
                </div>
            </div>
        </div>
    )
}

export default ToMessage;