import axios from "axios";
import { useEffect, useState } from "react";

const useFetch = (url: string) => {
    const [result, setResult] = useState({});
    const getData = async () => {
        try {
            const API_URL = process.env.REACT_APP_API_URL;
            let options = {
                method: 'get',
                url: `${API_URL}/${url}`,
                headers: {
                    'Content-Type': 'application/json'
                },
            };
            const response = await axios.request(options);
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