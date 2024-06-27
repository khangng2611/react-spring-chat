import React, {
    createContext,
    useCallback,
    useContext,
    useEffect,
    useState,
} from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { AxiosError } from "axios";
import Loader from "../component/loader"
import { apiClient } from "../services/api";
import { UserSchema } from "../constant/schema";

interface ContextSchema {
    details: UserSchema | null;
    // onlineUsers: Array<UserSchema>;
    // wsClient: WebSocketsClient | null;
    handleUserLogin: LOGIN_FUNC;
    handleUserLogout: () => Promise<void>;
}
interface StateSchema {
    isLoading: boolean;
    details: UserSchema | null;
}

type LOGIN_FUNC = (credentials: {
    username: string;
    fullName: string;
    password: string;
}) => Promise<void>;

const SessionContext = createContext<ContextSchema>({
    details: null,
    // onlineUsers: [],
    // wsClient: null,
    handleUserLogin: () => Promise.resolve(),
    handleUserLogout: () => Promise.resolve(),
});

const SessionContextProvider: React.FC<{ children: React.ReactNode }> = ({
    children,
}) => {
    const [state, setState] = useState<StateSchema>({
        isLoading: true,
        details: null,
    });
    // const [onlineUsers, setOnlineUsers] = useState<Array<UserSchema>>([]);
    // const [wsClient, setWsClient] = useState<WebSocketsClient | null>(null);
    // // const wsClient = useRef(WebSocketsClient.prototype);
    const navigate = useNavigate();
    const location = useLocation();

    const handleUserLogin: LOGIN_FUNC = useCallback(
        async (credentials, redirectPath = "/chat") => {
            let userDetails: UserSchema | null = null;
            try {
                const response = await apiClient.post("/signin", credentials);
                userDetails = response.data;
                if (userDetails === null)
                    throw new Error("User not found");
                setState({ isLoading: false, details: userDetails });
                // const wsClient = new WebSocketsClient(userDetails, setOnlineUsers);
                // setWsClient(wsClient);
                localStorage.setItem("session", JSON.stringify(userDetails));
                navigate(redirectPath, { replace: true });
            } catch (error: unknown) {
                let message = "Something went wrong";
                if (error instanceof AxiosError) {
                    message = error.response?.data.message ?? message;
                }
                throw new Error(message);
            }
        }, // eslint-disable-next-line
        []
    );

    const handleUserLogout = useCallback(
        async () => {
            try {
                // await apiClient.post("/logout");
                localStorage.removeItem("session");
                // wsClient?.onDisconnected();
            } finally {
                navigate("/login", { replace: true });
            }  // eslint-disable-next-line
        }, []
    );

    const handleIfUserAuthenticated = useCallback(
        async () => {
            setState({ isLoading: true, details: null });
            let userDetails: UserSchema;
            // const response = await apiClient.get("/refresh");
            // userDetails = response.data;

            let session = localStorage.getItem("session");
            if (session) {
                userDetails = JSON.parse(session);
                // const wsClient = new WebSocketsClient(userDetails, setOnlineUsers);
                // setWsClient(wsClient);
                setState({ isLoading: false, details: userDetails });
                if (location.pathname === "/login" || location.pathname === "/")
                    navigate("/chat", { replace: true });
            } else {
                navigate("/login", { replace: true });
                setState({ isLoading: false, details: null });
            }
            // eslint-disable-next-line
        }, []
    );

    useEffect(() => {
        handleIfUserAuthenticated(); // eslint-disable-next-line
    }, []);

    return (
        <SessionContext.Provider
            value={{ details: state.details, handleUserLogin, handleUserLogout, }}
        >
            {state.isLoading ? <Loader /> : children}
        </SessionContext.Provider>
    );
};

export const useSession = () => useContext(SessionContext);

export default SessionContextProvider;