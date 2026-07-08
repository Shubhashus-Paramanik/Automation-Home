import {useState} from 'react'
import {useNavigate} from 'react-router-dom'
import api from "../services/api"
import { toast } from "react-toastify";
import { useAuth } from "../context/AuthContext";

function LoginPage() {
  const navigate = useNavigate();
   const {login}=useAuth();
  const [email,setEmail] =useState("");
  const [password,setPassword]=useState("");

 

  const handelLogin=async(e)=>{
    e.preventDefault();
    try{
      
      // console.log(response.data);
      // const {login}=useAuth();
      const response=await api.post("/auth/login",{
        email,password
      });
      await login(response.data.token);
      toast.success("Login Successful");
      
      // localStorage.setItem("token",response.data.token);
      
      navigate("/dashboard");

    } catch (error) {
      console.log(error);
      console.log(error.message);
      console.log(error.response);

      if (
        error.response?.status === 401 ||
        error.response?.status === 403
      ) {
        toast.error("Invalid email or password");
      } else {
        toast.error("Login Failed");
      }
    }
  };
  return (
    <div>
     <h1>Login</h1>

     <form onSubmit={handelLogin}>
      <input type="email" placeholder='Email' value={email} onChange={(e)=>setEmail(e.target.value)} />

      <br /><br />

      <input type="password" placeholder='Password' value={password} onChange={(e)=>setPassword(e.target.value)}/>
      <br /><br />
      <button type='submit'>Login</button>
      
      </form>  
    </div>
  )
}

export default LoginPage