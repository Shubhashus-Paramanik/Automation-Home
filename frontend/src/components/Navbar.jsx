import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { useEffect, useState } from "react";
import api from "../services/api";

function Navbar() {
  // const [unreadCount, setUnreadCount] = useState(0);
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  // useEffect(() => {
  //   loadCount();

  //   const interval = setInterval(loadCount, 10000);

  //   return () => clearInterval(interval);
  // }, []);

  // const loadCount = async () => {
  //   try {
  //     const res = await api.get("/notifications/unread-count");

  //     setUnreadCount(res.data);
  //   } catch (err) {
  //     console.log(err);
  //   }
  // };

  const handleLogout = () => {
    logout();

    navigate("/");
  };

  return (
    <nav className="navbar navbar-dark bg-dark">
      <div className="container">
        <Link className="navbar-brand" to="/dashboard">
          Home Automation
        </Link>
        <span className="text-light me-3">Welcome {user?.name}</span>

        <div>
          <Link className="btn btn-outline-light me-2" to="/dashboard">
            Dashboard
          </Link>
          <Link to="/devices" className="btn btn-outline-light">
            Devices
          </Link>

          <Link className="btn btn-outline-light me-2" to="/homes">
            Homes
          </Link>

          <Link className="btn btn-outline-light me-2" to="/profile">
            Profile
          </Link>
         
          <Link className="btn btn-outline-light me-2" to="/schedules">
            Schedules
          </Link>
          <Link className="btn btn-outline-light me-2" to="/admin">
            Admin
          </Link>
          <Link className="btn btn-outline-light me-2" to="/shared">
            Shared
          </Link>
          <button className="btn btn-danger" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
