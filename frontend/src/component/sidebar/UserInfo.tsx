import React from "react";
import { useSession } from "../../context/SessionContext";

const UserInfo = () => {
    const { details } = useSession();
    return (
        <div className="flex flex-col items-center bg-blue-300 py-4 my-6 rounded-lg" >
            <img src={process.env.PUBLIC_URL + '/avatar.png'} alt="Avatar" className="w-16 h-16 rounded-full" />
            <div className="text-base font-semibold mt-2">{details?.fullName}</div>
            <div className="text-sm text-gray-500">{details?.username}</div>
            <div className="flex flex-row items-center mt-3">
                <div className="flex flex-col justify-center h-4 w-8 bg-green-500 rounded-full">
                    <div className="h-3 w-3 bg-white rounded-full self-end mr-1"></div>
                </div>
                <div className="leading-none ml-1 text-xs">Active</div>
            </div>
        </div>
    )
}

export default UserInfo;