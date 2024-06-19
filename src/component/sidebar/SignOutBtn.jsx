import { initWebSockets } from "../../services/websockets";

const SignOutBtn = () => {
    const handleClearCache = () => {
        initWebSockets(null);
        alert("Cache cleared!");
    }
    return (
        <div className="flex flex-col mt-3">
            <button
                className="text-sm border border-red-500 text-red-500 hover:bg-gray-100 rounded-lg py-2 px-4"
                onClick={handleClearCache}
            >
                Sign Out
            </button>
        </div>
    )
}

export default SignOutBtn;