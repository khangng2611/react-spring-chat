import React from "react";

const ToMessage = ({ content }: { content: Array<string> }) => {
    return (
        <div className="col-start-1 col-end-10 px-3 py-1 rounded-lg">
            <div className="flex flex-row items-end">
                <div className="flex items-center justify-center h-10 w-10 rounded-full bg-red-500 flex-shrink-0 text-sm overflow-hidden" >
                    <img
                        src="https://static.ybox.vn/2022/5/5/1653618217752-nguyen-nu-anh-thu35zz5xt5-avatar.png"
                        alt="ATOM"
                        className="h-full w-full"
                    />
                </div>
                <div className="relative ml-3 text-sm bg-white py-2 px-4 shadow rounded-xl min-w-fit max-w-full" >
                    <p className="break-words" style={{ whiteSpace: "pre-wrap" }}>
                        {
                            content && content.map((line, id) => (
                                <span key={id} >
                                    {/* <span style={{ fontWeight: "bold" }}>{line.split('=')[0]} {line.split('=')[1] ? "=" : ""}</span>
                                    <span>{line.split('=')[1]}</span> */}
                                    <span dangerouslySetInnerHTML={{ __html: line }}></span>
                                </span>
                            ))
                        }
                    </p>
                </div>
            </div>
        </div>
    )
}

export default ToMessage;