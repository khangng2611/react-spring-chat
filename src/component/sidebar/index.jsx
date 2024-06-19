import SignOutBtn from "./SignOutBtn";
import ContactList from "./ContactList";
import UserInfo from "./UserInfo";

const Sidebar = () => {
    const contactList = ["Henry Boyd","Christine Reid"]
    return (
        <div className="flex flex-col p-5 w-72 flex-shrink-0 justify-between">
            <div className="h-30 w-30 overflow-hidden self-center">
                <img
                    src={process.env.PUBLIC_URL + "/chat_logo.png"}
                    alt="ChatterBox"
                    className="h-20 w-20 object-cover"
                />
            </div>
            <UserInfo />
            <ContactList contactList={contactList}/>
            <SignOutBtn />
        </div>
    )
}

export default Sidebar;