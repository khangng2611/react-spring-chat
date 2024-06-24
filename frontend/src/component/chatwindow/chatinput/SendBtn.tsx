import React from "react";

const SendBtn = ({ handleOnCLick }: { handleOnCLick: () => void }) => {
    return (
        <div className="ml-4">
            <button
                className="flex items-center justify-center rounded-xl bg-sky-600 hover:bg-sky-700 text-white px-4 py-1 flex-shrink-0"
                onClick={handleOnCLick}
            >
                <span>Send</span>
                <span className="ml-2">
                    <svg
                        className="w-4 h-4 transform rotate-45 -mt-px"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth="2"
                            d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
                        ></path>
                    </svg>
                </span>
            </button>
        </div>
    )
}

export default SendBtn;