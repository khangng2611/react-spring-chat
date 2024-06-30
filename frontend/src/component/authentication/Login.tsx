import React, { useCallback, useState } from 'react';
import { useSession } from '../../context/SessionContext';
import { TONE_COLORS } from '../../constant/color';

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
            <h2
                className="text-4xl font-bold mb-8"
                style={{color: TONE_COLORS.PRIMARY}}
            >
                Join ChatterBox !
            </h2>
            <form onSubmit={handleSubmit} className="flex flex-col bg-blue-200 shadow-md rounded px-12 pt-10 pb-12 mb-8 w-96">
                <div className="mb-6">
                    <label className="block text-gray-700 text-lg font-bold mb-4" htmlFor="email">
                        Username:
                    </label>
                    <input
                        className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="username"
                        type="text"
                        value={userInfo.username}
                        placeholder='Enter your username'
                        minLength={6}
                        required
                        maxLength={100}
                        onChange={handleFormChange}
                    />
                </div>
                <div className="mb-6">
                    <label className="block text-gray-700 text-lg font-bold mb-4" htmlFor="email">
                        Full name:
                    </label>
                    <input
                        className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="fullName"
                        type="text"
                        value={userInfo.fullName}
                        placeholder='Enter your full name'
                        minLength={6}
                        required
                        maxLength={100}
                        onChange={handleFormChange}
                    />
                </div>
                {/* <div className="mb-6">
                    <label className="block text-gray-700 text-lg font-bold mb-4" htmlFor="password">
                        Password:
                    </label>
                    <input
                        className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="password"
                        type="password"
                        value={userInfo.password}
                        minLength={6}
                        onChange={handleFormChange}
                    />
                </div> */}
                <button
                    className="bg-sky-600 hover:bg-sky-700 text-white font-bold py-3 px-6 rounded focus:outline-none focus:shadow-outline"
                    type="submit"
                >
                    Login
                </button>
            </form>
        </div>
    );
};

export default Login;