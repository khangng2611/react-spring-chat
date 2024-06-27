import React from 'react';
import { UserSchema } from "../../constant/schema";
import { TONE_COLORS } from '../../constant/color';

const PublicChatBtn = ({ selectedUser, setSelectedUser }: { selectedUser: UserSchema | null, setSelectedUser: (user: UserSchema) => void }) => {
    const handleOnCLick = () => {
        setSelectedUser({
            id: -1,
            fullName: 'Public Chatroom',
            username: 'Public Chatroom',
            status: 'ONLINE'
        });
    }

    return (
        <div className="flex flex-col space-y-1 -mx-2">
            <button
                className={`flex flex-row items-center hover:bg-gray-100 rounded-xl p-2 ${selectedUser && selectedUser.id === -1 ? 'bg-gray-100' : ''}`}
                onClick={() => handleOnCLick()}
            >
                <div
                    className="flex items-center justify-center h-8 w-8 bg-pink-500 rounded-full"
                    style={{ backgroundColor: TONE_COLORS.PRIMARY_HOVER }}
                >
                    <img src={process.env.PUBLIC_URL + '/public_chat.png'} alt="public-chat" className="w-3/5" />
                </div>
                <div className="ml-2 text-sm font-semibold justify-center">Public Chatroom</div>
            </button>
        </div>
    )
}

export default PublicChatBtn;