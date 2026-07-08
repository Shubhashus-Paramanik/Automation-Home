import { createContext, useContext, useEffect, useState } from "react";
import api from "../services/api";

const AuthContext = createContext();

export function AuthProvider({ children }) {

    const [token, setToken] = useState(null);
    const [user, setUser]=useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
       const loadUser=async()=>{
        const savedToken = localStorage.getItem("token");

        if (savedToken) {
            setToken(savedToken);
        
        try{
            const res = await api.get("/auth/me");

                setUser(res.data);

            }catch(err){

                localStorage.removeItem("token");

                setToken(null);
                setUser(null);

            }
        }

        setLoading(false);
    };
    loadUser();

    }, []);

    const login = async(jwt) => {
        localStorage.setItem("token", jwt);
        setToken(jwt);

        try {
            const res = await api.get("/auth/me");
            setUser(res.data);
        } catch (err) {
            localStorage.removeItem("token");
            setToken(null);
            setUser(null);
            throw err;
        }
    };

    const logout = () => {

        localStorage.removeItem("token");

        setToken(null);
        setUser(null);

    };

    return (

        <AuthContext.Provider
            value={{
                token,
                user,
                setUser,
                login,
                logout,
                loading,
                isAuthenticated: !!token
            }}
        >

            {children}

        </AuthContext.Provider>

    );

}

export const useAuth = () => useContext(AuthContext);