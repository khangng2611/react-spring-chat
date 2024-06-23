import React, { useEffect, useState } from 'react';
import { AVATAR_COLORS } from '../../constant/color'
import { OnlineListSchema, useSession } from '../../context/SessionContext';
import useFetch from '../../hook/useFetch';

const ContactList = ({ setSelectedId }: { setSelectedId: (id: number) => void }) => {
  const { details, newOnlineUser } = useSession();
  const [allOnlineUser, setAllOnlineUser] = useState<Array<OnlineListSchema>>([])
  const fetchedOnlineUser = useFetch('users') as Array<OnlineListSchema>;
  useEffect(() => {
    // filter the api fetch user without the self id
    const filteredFetch = fetchedOnlineUser.length ? fetchedOnlineUser.filter(user => user.id !== details?.id) : [];
    // filter newOnlineUser without the user that already fetched
    const filteredNewOnlineUser = newOnlineUser.filter(user => !filteredFetch.some(fetchUser => fetchUser.id === user.id));
    // get all offline users in newOnlineUser
    const offlineUser = newOnlineUser.filter(user => user.status === 'OFFLINE');
    console.log('offlineUser', offlineUser);
    // fillter [...filteredNewOnlineUser, ...filteredFetch] without offline users
    let offlineRemovedUser = [...filteredNewOnlineUser, ...filteredFetch].filter(user => !offlineUser.some(offline => offline.id === user.id));
    setAllOnlineUser(offlineRemovedUser);
  }, [fetchedOnlineUser, details?.id, newOnlineUser]);

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
        {allOnlineUser.length > 0 && allOnlineUser.map((user) => (
          <button
            key={user.id}
            className="flex flex-row items-center hover:bg-gray-100 rounded-xl p-2"
            onClick={() => setSelectedId(user.id)}
          >
            <div className={`flex items-center justify-center h-8 w-8 bg-${AVATAR_COLORS[user.id % AVATAR_COLORS.length]}-200 rounded-full`}>
              {user.fullName[0]}
            </div>
            <div className="ml-2 text-sm font-semibold">{user.fullName}</div>
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