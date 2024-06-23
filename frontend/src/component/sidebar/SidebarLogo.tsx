import React from "react";
import { TONE_COLORS } from "../../constant/color";

const SidebarLogo = () => {
    return (
        <div className="flex flex-row h-30 w-30 overflow-hidden self-center flex-">
            <img
                src={process.env.PUBLIC_URL + "/chat_logo.png"}
                alt="ChatterBox"
                className="h-20 w-20 object-cover"
            />
            <div className="ml-2 text-2xl font-semibold self-center" style={{ color: TONE_COLORS.PRIMARY }}>ChatterBox</div>
        </div>
    )
}

export default SidebarLogo;