import React, {
    createContext,
    useCallback,
    useContext,
    useEffect,
    useRef,
    useState,
} from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { AxiosError } from "axios";
import Loader from "../component/loader"
import WebSocketsClient from "../services/websockets";
import { apiClient } from "../services/api";

export interface UserDetailsSchema {
    id: number,
    username: string;
    fullName: string;
}

interface StateSchema {
    isLoading: boolean;
    details: UserDetailsSchema | null;
}

type LOGIN_FUNC = (credentials: {
    username: string;
    fullName: string;
    password: string;
}) => Promise<void>;

export interface OnlineListSchema {
    id: number;
    username: string;
    fullName: string;
}

interface ContextSchema {
    details: UserDetailsSchema | null;
    newOnlineUser: Array<OnlineListSchema>;
    wsClient: WebSocketsClient | null;
    handleUserLogin: LOGIN_FUNC;
    handleUserLogout: () => Promise<void>;
}

const Context = createContext<ContextSchema>({
    details: null,
    newOnlineUser: [],
    wsClient: null,
    handleUserLogin: () => Promise.resolve(),
    handleUserLogout: () => Promise.resolve(),
});

const SessionContext: React.FC<{ children: React.ReactNode }> = ({
    children,
}) => {
    const [state, setState] = useState<StateSchema>({
        isLoading: true,
        details: null,
    });
    const [newOnlineUser, setNewOnlineUser] = useState<Array<OnlineListSchema>>([]);
    const wsClient = useRef(WebSocketsClient.prototype);
    const navigate = useNavigate();
    const location = useLocation();

    const handleUserLogin: LOGIN_FUNC = useCallback(
        async (credentials, redirectPath = "/chat") => {
            let userDetails: UserDetailsSchema | null = null;
            try {
                const response = await apiClient.post("/signin", credentials);
                userDetails = response.data;
                if (userDetails === null)
                    throw new Error("User not found");
                setState({ isLoading: false, details: userDetails });
                wsClient.current = new WebSocketsClient(userDetails, setNewOnlineUser);
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
                wsClient?.current.onDisconnected();
            } finally {
                navigate("/login", { replace: true });
            }  // eslint-disable-next-line
        }, []
    );

    const handleIfUserAuthenticated = useCallback(
        async () => {
            setState({ isLoading: true, details: null });
            let userDetails: UserDetailsSchema;
            // const response = await apiClient.get("/refresh");
            // userDetails = response.data;

            let session = localStorage.getItem("session");
            if (session) {
                userDetails = JSON.parse(session);
                wsClient.current = new WebSocketsClient(userDetails, setNewOnlineUser);
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
        <Context.Provider
            value={{ details: state.details, newOnlineUser: newOnlineUser, wsClient: wsClient.current, handleUserLogin, handleUserLogout, }}
        >
            {state.isLoading ? <Loader /> : children}
        </Context.Provider>
    );
};

export const useSession = () => useContext(Context);

export default SessionContext;