import { useEffect, useState } from "react";
import api from "../services/api";
import Navbar from "../components/Navbar";
import { toast } from "react-toastify";
import LoadingSpinner from "../components/LoadingSpinner";
import { useAuth } from "../context/AuthContext";

function ProfilePage() {
  const {setUser}=useAuth();
  const [loading, setLoading] = useState(true);

  const [profile, setProfile] = useState({
    name: "",
    email: ""
  });

  const [password, setPassword] = useState({
    currentPassword: "",
    newPassword: ""
  });

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile = async () => {

    try {

      const res = await api.get("/auth/profile");
      setUser(res.data);
      setProfile(res.data);


    } catch {

      toast.error("Unable to load profile");

    } finally {

      setLoading(false);

    }
  };

  const updateProfile = async () => {

    try {

      const res = await api.put(
        "/auth/profile",
        {
          name: profile.name
        }
      );

      setProfile(res.data);
// Update AuthContext
        setUser(res.data);
      toast.success("Profile Updated");

    } catch {

      toast.error("Update Failed");

    }
  };

  const changePassword = async () => {

    try {

      await api.put(
        "/auth/change-password",
        password
      );

      toast.success("Password Changed");

      setPassword({
        currentPassword: "",
        newPassword: ""
      });

    } catch {

      toast.error("Password Change Failed");

    }
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <>
      {/* <Navbar /> */}

      <div className="container mt-4">

        <h2>My Profile</h2>

        <div className="card p-4 mb-4">

          <h4>Profile</h4>

          <label>Name</label>

          <input
            className="form-control mb-3"
            value={profile.name}
            onChange={(e)=>
              setProfile({
                ...profile,
                name:e.target.value
              })
            }
          />

          <label>Email</label>

          <input
            className="form-control mb-3"
            value={profile.email}
            disabled
          />

          <button
            className="btn btn-primary"
            onClick={updateProfile}
          >
            Save Profile
          </button>

        </div>

        <div className="card p-4">

          <h4>Change Password</h4>

          <input
            type="password"
            className="form-control mb-3"
            placeholder="Current Password"
            value={password.currentPassword}
            onChange={(e)=>
              setPassword({
                ...password,
                currentPassword:e.target.value
              })
            }
          />

          <input
            type="password"
            className="form-control mb-3"
            placeholder="New Password"
            value={password.newPassword}
            onChange={(e)=>
              setPassword({
                ...password,
                newPassword:e.target.value
              })
            }
          />

          <button
            className="btn btn-danger"
            onClick={changePassword}
          >
            Change Password
          </button>

        </div>

      </div>
    </>
  );
}

export default ProfilePage;