import axios from "axios";

axios.defaults.withCredentials = true;

const axiosInstance = axios.create({
    baseURL: "http://localhost:3000",
    headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("your-token-key")}`,
        "Accept": "application/json",
    },
});

axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem("jwt");
    if (token) {
    config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
});
export default axiosInstance;