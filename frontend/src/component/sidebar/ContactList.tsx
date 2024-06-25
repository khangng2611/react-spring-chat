import React, { useEffect, useState } from 'react';
import { AVATAR_COLORS } from '../../constant/color'
import { OnlineUserSchema, useSession } from '../../context/SessionContext';
import useFetch from '../../hook/useFetch';

const ContactList = ({ selectedUser, setSelectedUser }: { selectedUser: OnlineUserSchema | null, setSelectedUser: (user: OnlineUserSchema) => void }) => {
  const { details, newOnlineUsers } = useSession();
  const [allOnlineUser, setAllOnlineUser] = useState<Array<OnlineUserSchema>>([])
  const fetchedOnlineUser = useFetch('users') as Array<OnlineUserSchema>;
  useEffect(() => {
    // filter the api fetch user without the self id
    const filteredFetch = fetchedOnlineUser.length ? fetchedOnlineUser.filter(user => user.id !== details?.id) : [];
    // filter newOnlineUsers without the user that already fetched
    const filteredNewOnlineUser = newOnlineUsers.filter(user => !filteredFetch.some(fetchUser => fetchUser.id === user.id));
    // get all offline users in newOnlineUsers
    const offlineUser = newOnlineUsers.filter(user => user.status === 'OFFLINE');
    // fillter [...filteredNewOnlineUser, ...filteredFetch] without offline users
    let offlineRemovedUser = [...filteredNewOnlineUser, ...filteredFetch].filter(user => !offlineUser.some(offline => offline.id === user.id));
    setAllOnlineUser(offlineRemovedUser);
  }, [fetchedOnlineUser, details?.id, newOnlineUsers]);

  const handleOnCLick = (onlineUser: OnlineUserSchema) => {
    setSelectedUser(onlineUser);
  }

  return (
    <div className="flex flex-col mt-8 flex-1">
      <div className="flex flex-row items-center justify-between text-xs">
        <span className="font-bold">Active Friends</span>
        {/* <span
          className="flex items-center justify-center bg-gray-300 h-4 w-4 rounded-full"
        >4</span
        > */}
      </div>
      <div className="flex flex-col space-y-1 mt-4 -mx-2 h-48 overflow-y-auto">
        {allOnlineUser.length > 0 && allOnlineUser.map((onlineUser) => (
          <button
            key={onlineUser.id}
            className={`flex flex-row items-center hover:bg-gray-100 rounded-xl p-2 ${selectedUser && onlineUser.id === selectedUser.id ? 'bg-gray-100' : ''}`}
            onClick={() => handleOnCLick(onlineUser)}
          >
            <div className={`flex items-center justify-center h-8 w-8 ${AVATAR_COLORS.at(onlineUser.id%AVATAR_COLORS.length)} rounded-full`}>
              {onlineUser.fullName[0].toUpperCase()}
            </div>
            <div className="ml-2 text-sm font-semibold">{onlineUser.fullName}</div>
            {/* {contact.unreadMessages > 0 && (
              <div
                className="flex items-center justify-center ml-auto text-xs text-white bg-red-500 h-4 w-4 rounded leading-none"
              >
                {contact.unreadMessages}
              </div>
            )} */}
          </button>
        ))}
      </div>
    </div>
  )
}

export default ContactList;