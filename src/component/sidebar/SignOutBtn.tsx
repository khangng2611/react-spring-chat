import React from "react";
import { useSession } from "../../context/SessionContext";

const SignOutBtn = () => {
    const { handleUserLogout } = useSession();   
    return (
        <div className="flex flex-col mt-3">
            <button
                className="text-sm border border-red-500 text-red-500 hover:bg-gray-100 rounded-lg py-2 px-4"
                onClick={() => handleUserLogout()}
            >
                Sign Out
            </button>
        </div>
    )
}

export default SignOutBtn;