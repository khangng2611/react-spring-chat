import React, { useState } from 'react';
import Sidebar from '../sidebar';
import ChatWindow from '../chatwindow';
import { OnlineUserSchema } from '../../context/SessionContext';

const Container = () => {
    // const { details } = useSession();
    const [selectedUser, setSelectedUser] = useState<OnlineUserSchema | null>(null);
    // const [fetchedMessages, setFetchedMessages] = useState<Array<MessageSchema>>([]);
    // const fetch = useFetch(`messages/${details?.id}/${selectedUser?.id}`) as Array<MessageSchema>;
    // useEffect(() => {
    //     console.log("Fetched messages", fetch);
    //     setFetchedMessages(fetch);
    // }, [selectedUser?.id, fetch])
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
