import React, { useState } from 'react';
import Sidebar from '../sidebar';
import ChatWindow from '../chatwindow';

const Container = () => {
    const [selectedId, setSelectedId] = useState<number | null>(null);
    return (
        <div className="flex h-screen antialiased text-gray-800">
            <div className="flex flex-row h-full w-full overflow-x-hidden">
                <Sidebar setSelectedId={setSelectedId} />
                <ChatWindow selectedId={selectedId} />
            </div>
        </div>
    );
};

export default Container;
