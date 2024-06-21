import React, {
    createContext,
    useCallback,
    useContext,
    useEffect,
    useState,
} from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { AxiosError } from "axios";
//   import apiClient from "./apiClient";
import Loader from "../component/loader"

interface UserDetailsSchema {
    name: string;
    username: string;
}

interface StateSchema {
    isLoading: boolean;
    details: UserDetailsSchema | null;
}

type LOGIN_FUNC = (credentials: {
    username: string;
    password: string;
}) => Promise<void>;

interface ContextSchema {
    details: UserDetailsSchema | null;
    handleUserLogin: LOGIN_FUNC;
    handleUserLogout: () => Promise<void>;
}

const Context = createContext<ContextSchema>({
    details: null,
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
    const navigate = useNavigate();
    const location = useLocation();

    const handleUserLogin: LOGIN_FUNC = useCallback(
        async (credentials, redirectPath = "/chat") => {
            let userDetails: UserDetailsSchema | null = null;
            try {
                //   const response = await apiClient.post("/login", {
                //     credentials,
                //   });
                // userDetails = response.data;
                userDetails = {
                    name: 'Test',
                    username: credentials.username,
                }
                localStorage.setItem("session", credentials.username);
                setState({ isLoading: false, details: userDetails });
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
            } finally {
                navigate("/login", { replace: true });
            }  // eslint-disable-next-line
        }, []
    );

    const handleIfUserAuthenticated = useCallback(
        async () => {
            setState({ isLoading: true, details: null });
            let userDetails: UserDetailsSchema | null = null;
            try {
                // const response = await apiClient.get("/refresh");
                // userDetails = response.data;

                let session = localStorage.getItem("session");
                if (session) {
                    userDetails = {
                        name: 'Test',
                        username: session,
                    }
                } else {
                    throw new Error("No session found");
                }

                if (location.pathname === "/login")
                    navigate("/chat", { replace: true });
            } catch {
                navigate("/login", { replace: true });
            } finally {
                setState({ isLoading: false, details: userDetails });
            }  // eslint-disable-next-line
        }, [] 
    );

    useEffect(() => {
        handleIfUserAuthenticated(); // eslint-disable-next-line
    }, []); 

    return (
        <Context.Provider
            value={{ details: state.details, handleUserLogin, handleUserLogout }}
        >
            {state.isLoading ? <Loader /> : children}
        </Context.Provider>
    );
};

export const useSession = () => useContext(Context);

export default SessionContext;