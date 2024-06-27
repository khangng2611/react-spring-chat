import React from "react";
import SignOutBtn from "./SignOutBtn";
import ContactList from "./ContactList";
import UserInfo from "./UserInfo";
import SidebarLogo from "./SidebarLogo";
import PublicChatBtn from "./PublicChatBtn";
import { UserSchema } from "../../constant/schema";

const Sidebar = ({ selectedUser, setSelectedUser }: { selectedUser: UserSchema | null, setSelectedUser: (user: UserSchema) => void }) => {
    return (
        <div className="flex flex-col p-5 w-72 flex-shrink-0 justify-between">
            <SidebarLogo />
            <UserInfo />
            <PublicChatBtn selectedUser={selectedUser} setSelectedUser={setSelectedUser}/>
            <ContactList selectedUser={selectedUser} setSelectedUser={setSelectedUser} />
            <SignOutBtn />
        </div>
    )
}

export default Sidebar;