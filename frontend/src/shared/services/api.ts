import axios from "axios";

const DEFAULT_LOCAL_API_URL: string = "http://localhost:8080/api";

export const api = axios.create({
    baseURL: DEFAULT_LOCAL_API_URL,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
    }
});