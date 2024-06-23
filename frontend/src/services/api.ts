import axios from 'axios';

export const apiClient = axios.create({
    baseURL: process.env.REACT_APP_BE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});
