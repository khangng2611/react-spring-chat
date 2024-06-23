import React from "react";
import { useSession } from "../../context/SessionContext";

const SignOutBtn = () => {
    const { handleUserLogout } = useSession();   
    return (
        <div className="flex flex-col mt-3">
            <button
                className="border border-sky-600 text-sky-800 hover:bg-gray-100 rounded-lg py-2 px-3 text-sm w-4/5 self-center"
                onClick={() => handleUserLogout()}
            >
                Sign Out
            </button>
        </div>
    )
}

export default SignOutBtn;