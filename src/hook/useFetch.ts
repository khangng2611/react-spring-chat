import { apiClient } from "../services/api";
import { useEffect, useState } from "react";

const useFetch = (url: string) => {
    const [result, setResult] = useState({});
    const getData = async () => {
        try {
            const response = await apiClient.get(url);
            setResult(response.data);
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
        }
    }
    useEffect(() => {
        getData();
        // eslint-disable-next-line
    }, []);
    return result;
}

export default useFetch;