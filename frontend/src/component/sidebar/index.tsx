import React from "react";
import SignOutBtn from "./SignOutBtn";
import ContactList from "./ContactList";
import UserInfo from "./UserInfo";
import SidebarLogo from "./SidebarLogo";

const Sidebar = ({ setSelectedId }: { setSelectedId: (id: number) => void }) => {
    return (
        <div className="flex flex-col p-5 w-72 flex-shrink-0 justify-between">
            <SidebarLogo />
            <UserInfo />
            <ContactList setSelectedId={setSelectedId} />
            <SignOutBtn />
        </div>
    )
}

export default Sidebar;