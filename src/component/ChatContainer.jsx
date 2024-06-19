import React, { useEffect, useState } from 'react';
import Sidebar from './sidebar';
import ChatWindow from './chatwindow';

const ChatBox = () => {
    return (
        <div className="flex h-screen antialiased text-gray-800">
            <div className="flex flex-row h-full w-full overflow-x-hidden">
                <Sidebar />
                <ChatWindow />
            </div>
        </div>
    );
};

export default ChatBox;