import { onDisconnected } from "../../services/websockets";
import React from "react";

const SignOutBtn = () => {
    const handleSignOut = () => {
        localStorage.removeItem('session')
        onDisconnected();
    }
    return (
        <div className="flex flex-col mt-3">
            <button
                className="text-sm border border-red-500 text-red-500 hover:bg-gray-100 rounded-lg py-2 px-4"
                onClick={handleSignOut}
            >
                Sign Out
            </button>
        </div>
    )
}

export default SignOutBtn;