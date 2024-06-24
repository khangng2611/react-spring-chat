import React, { useState } from 'react';
import Sidebar from '../sidebar';
import ChatWindow from '../chatwindow';
import { OnlineUserSchema } from '../../context/SessionContext';

const Container = () => {
    const [selectedUser, setSelectedUser] = useState<OnlineUserSchema | null>(null);
    return (
        <div className="flex h-screen antialiased text-gray-800">
            <div className="flex flex-row h-full w-full overflow-x-hidden">
                <Sidebar selectedUser={selectedUser} setSelectedUser={setSelectedUser} />
                { selectedUser && <ChatWindow selectedUser={selectedUser} /> }
            </div>
        </div>
    );
};

export default Container;
