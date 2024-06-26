import React, { useCallback, useState } from 'react';
import { useSession } from '../../context/SessionContext';

const Login = () => {
    const [userInfo, setUserInfo] = useState({
        username: '',
        fullName: '',
        password: ''
    })
    const { handleUserLogin } = useSession();

    const handleFormChange = useCallback(
        (e: React.ChangeEvent<HTMLInputElement>) => {
            const { name, value } = e.target;
            setUserInfo((old) => ({ ...old, [name]: value }));
        },
        []
    )

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        handleUserLogin(userInfo);
    };

    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <h2 className="text-2xl font-bold mb-4">Login</h2>
            <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="email">
                        Full Name:
                    </label>
                    <input
                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="fullName"
                        type="text"
                        value={userInfo.fullName}
                        minLength={6}
                        maxLength={100}
                        onChange={handleFormChange}
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="email">
                        Username:
                    </label>
                    <input
                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="username"
                        type="text"
                        value={userInfo.username}
                        minLength={6}
                        maxLength={100}
                        onChange={handleFormChange}
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
                        Password:
                    </label>
                    <input
                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="password"
                        type="password"
                        value={userInfo.password}
                        minLength={6}
                        onChange={handleFormChange}
                    />
                </div>
                <button
                    className="bg-sky-600 hover:bg-sky-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                    type="submit"
                >
                    Login
                </button>
            </form>
        </div>
    );
};

export default Login;