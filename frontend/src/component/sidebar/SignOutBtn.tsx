import React from "react";
import { useSession } from "../../context/SessionContext";
import { useWebsockets } from "../../context/WebsocketsContext";

const SignOutBtn = () => {
    const { onDisconnected } = useWebsockets();
    const { handleUserLogout } = useSession();
    const handleClick = () => { 
        if (onDisconnected) {
            onDisconnected();
        }
        handleUserLogout();
    }
    return (
        <div className="flex flex-col mt-3">
            <button
                className="border border-sky-600 text-sky-800 hover:bg-gray-100 rounded-lg py-2 px-3 text-sm w-4/5 self-center"
                onClick={() => handleClick()}
            >
                Sign Out
            </button>
        </div>
    )
}

export default SignOutBtn;