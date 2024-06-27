import React, { useState } from 'react';
import Sidebar from '../sidebar';
import ChatWindow from '../chatwindow';
import { UserSchema } from '../../constant/schema';
import { useWebsockets } from '../../context/WebsocketsContext';

const Container = () => {
    const [selectedUser, setSelectedUser] = useState<UserSchema | null>(null);
    const { onDisconnected } = useWebsockets();

    window.onbeforeunload = () => {
        if (onDisconnected) {
            onDisconnected();
        }
    };

    return (
        <div className="flex h-screen antialiased text-gray-800">
            <div className="flex flex-row h-full w-full overflow-x-hidden">
                <Sidebar selectedUser={selectedUser} setSelectedUser={setSelectedUser} />
                {selectedUser && <ChatWindow selectedUser={selectedUser} />}
            </div>
        </div>
    );
};

export default Container;
