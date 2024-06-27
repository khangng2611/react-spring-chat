import React, { useEffect } from 'react';
import { AVATAR_COLORS } from '../../constant/color'
import { UserSchema } from '../../constant/schema';
import { useWebsockets } from '../../context/WebsocketsContext';

const ContactList = ({ selectedUser, setSelectedUser }: { selectedUser: UserSchema | null, setSelectedUser: (user: UserSchema) => void }) => {
  const { onlineUsers } = useWebsockets();
  const onlineUsersArray = Array.from(onlineUsers.values()).flat();

  useEffect(() => {
  }, [onlineUsers])

  const handleOnCLick = (onlineUser: UserSchema) => {
    setSelectedUser(onlineUser);
  }

  return (
    <div className="flex flex-col mt-8 flex-1">
      <div className="flex flex-row items-center justify-between text-xs">
        <span className="font-bold">Active Friends</span>
        <span className="flex items-center justify-center bg-green-400 h-4 w-4 p-3 rounded-full">{onlineUsersArray.length}</span>
      </div>
      <div className="flex flex-col space-y-1 mt-4 -mx-2 h-48 overflow-y-auto">
        {onlineUsersArray.length > 0 && onlineUsersArray.map((onlineUser) => (
          <button
            key={onlineUser.id}
            className={`flex flex-row items-center hover:bg-gray-100 rounded-xl p-2 ${selectedUser && onlineUser.id === selectedUser.id ? 'bg-gray-100' : ''}`}
            onClick={() => handleOnCLick(onlineUser)}
          >
            <div className={`flex items-center justify-center h-8 w-8 ${AVATAR_COLORS.at(onlineUser.id % AVATAR_COLORS.length)} rounded-full`}>
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