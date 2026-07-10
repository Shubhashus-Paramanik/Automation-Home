import axios from "axios";

const api=axios.create({
    // baseURL:"http://localhost:8080/api",
        baseURL:"https://automation-home.onrender.com/api",

});

api.interceptors.request.use((config) => {

    const token = localStorage.getItem("token");

    const publicUrls = [
        "/auth/login",
        "/auth/register"
    ];

    const isPublic =
        publicUrls.some(
            url => config.url?.includes(url)
        );

    if (token && !isPublic) {
        config.headers.Authorization =
            `Bearer ${token}`;
    }

    return config;

}, (error) => Promise.reject(error));
export default api;