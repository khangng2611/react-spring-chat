import axios from 'axios';
import { MessageSchema, UserSchema } from '../constant/schema';

export const apiClient = axios.create({
    baseURL: process.env.REACT_APP_BE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

export const getOnlineUsers = async () => {
    try {
        const response = await apiClient.get('users');
        return response.data as Array<UserSchema>;
    } catch (error: any) {
        if (error.response) {
            console.log('error.response');
            console.log(error.response);
            // throw new Error(error.response.data);
        } else if (error.message) {
            console.log('error.message');
            console.log(error.message);
            // throw new Error(error.message);
        } else if (error.request) {
            console.log('error.request');
            console.log(error.request);
            // throw new Error(error.request);
        }
        return [];
    }
}


export const getAllMessages = async (userId1: number, userId2: number) => {
    try {
        const response = await apiClient.get(`messages/${userId1}/${userId2}`);
        return response.data as Array<MessageSchema>;
    } catch (error: any) {
        if (error.response) {
            console.log('error.response');
            console.log(error.response);
            // throw new Error(error.response.data);
        } else if (error.message) {
            console.log('error.message');
            console.log(error.message);
            // throw new Error(error.message);
        } else if (error.request) {
            console.log('error.request');
            console.log(error.request);
            // throw new Error(error.request);
        }
        return [];
    }
}
