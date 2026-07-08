import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../services/api";
// import Navbar from "../components/Navbar";
import { toast } from "react-toastify";
import LoadingSpinner from "../components/LoadingSpinner";
import {useNavigate} from "react-router-dom";
import {
    connectSocket,
    disconnectSocket
}
from "../services/socket";


function DeviceDetailsPage() {
  const navigate = useNavigate();
  const { deviceId } = useParams();
  const [editing, setEditing] = useState(false);
  const [name, setName] = useState("");
  const [homes, setHomes] = useState([]);
  const [homeId, setHomeId] = useState("");

  const [info, setInfo] = useState(null);
  const [state, setState] = useState(null);
  const [saving, setSaving] = useState(false);
  const [shareEmail, setShareEmail] = useState("");
  const [role, setRole] = useState("VIEWER");
  const [permissions, setPermissions]= useState(null);
  const [sharing,setSharing] = useState(false);
  const [controlling,setControlling]=useState("");


  const loadData = async () => {
    try {
      const [infoRes, stateRes, logsRes, homesRes, permissionsRes] = await Promise.all([
        api.get(`/devices/${deviceId}/info`),
        api.get(`/devices/${deviceId}/state`),
        api.get("/homes"),
        api.get(`/devices/${deviceId}/permissions`),
      ]);

      setInfo(infoRes.data);
      setState(stateRes.data);
      setHomes(homesRes.data);
      setPermissions(permissionsRes.data);
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
   const initialize = async () => {
        await loadData();
    };

    initialize();

    connectSocket(
        deviceId,
        
        
        (message) => {

    console.log(
        "WEBSOCKET MESSAGE:",
        message
    );

    toast.success(
        "SOCKET RECEIVED"
    );

    // update current state instantly
    setState(prev=>({
        ...prev,
        lightState: message.lightState,

        fanState: message.fanState,

        tvState: message.tvState,

        plugState: message.plugState,
    }));

    // update device info
    setInfo(prev => ({
        ...prev,
        online:
            message.online
    }));

   
}
    );

    return () => {
        disconnectSocket();
    };
  }, [deviceId]);

  useEffect(() => {
    if (info) {
      setName(info.name);
      setHomeId(info.homeId);
    }
  }, [info]);

  if (!info) {
    return <LoadingSpinner />;
  }
  const updateDevice = async () => {
    setSaving(true);

    try {
      await api.put(`/devices/${deviceId}`, {
        name,
        homeId,
      });

      toast.success("Device Updated");
      // setTimeout(()=>{
      //   window.location.reload();
      // },1000)
      await loadData();
      setEditing(false);
    } catch (err) {
      toast.error("Unable to Update Device");
    } finally {
      setSaving(false);
    }
  };

const shareDevice = async () => {

    if (!shareEmail) {
        toast.error("Please enter email");
        return;
    }
    setSharing(true);

    try {
        await api.post("/device-access", {
            deviceId,
            email: shareEmail,
            role
        });

        toast.success("Device Shared");

        setShareEmail("");
        setRole("VIEWER");

    } catch {
        toast.error("Share Failed");
    }
    finally{
   setSharing(false);
}
};
const deleteDevice = async () => {

  if (!window.confirm(
      "Are you sure you want to delete this device?"
  )) {
    return;
  }

  try {

    await api.delete(
      `/devices/${deviceId}`
    );

    toast.success(
      "Device Deleted"
    );

    navigate("/devices");

  } catch {

    toast.error(
      "Delete Failed"
    );

  }
};

const controlDevice = async (type) => {
    setControlling(type);
    try {
        const res = await api.post("/devices/control", {
            deviceId,
            lightState:
                type === "LIGHT"
                    ? !state.lightState
                    : state.lightState,
            fanState:
                type === "FAN"
                    ? !state.fanState
                    : state.fanState,
            tvState:
                type === "TV"
                    ? !state.tvState
                    : state.tvState,
            plugState:
                type === "PLUG"
                    ? !state.plugState
                    : state.plugState,
        });

        const updatedState = {
            lightState: res.data.lightState,
            fanState: res.data.fanState,
            tvState: res.data.tvState,
            plugState: res.data.plugState,
        };

        setState((prev) => ({
            ...prev,
            ...updatedState,
        }));

        setInfo((prev) => ({
            ...prev,
            online: res.data.online ?? prev.online,
        }));

        toast.success(`${type} updated`);
    } catch {
        toast.error("Control failed");
    } finally {
        setControlling("");
    }
};
  return (
    <>
      {/* <Navbar /> */}

      <div className="container mt-4">
        <h2>
    {info.name}
    {info.online ? " 🟢" : " 🔴"}
</h2>

        <p>
          <strong>Device ID:</strong> {info.deviceId}
        </p>

<div className="mb-3">

    {permissions?.canEdit && (
        <button
            className="btn btn-warning me-2"
            onClick={() => setEditing(!editing)}
        >
            Edit Device
        </button>
    )}

    {permissions?.canDelete && (
        <button
            className="btn btn-danger"
            onClick={deleteDevice}
        >
            Delete Device
        </button>
    )}

</div>
        {editing && (
          <div className="card p-3 mb-3">
            <input
              className="form-control mb-2"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

            <select
              className="form-select mb-2"
              value={homeId}
              onChange={(e) => setHomeId(e.target.value)}
            >
              {homes.map((home) => (
                <option key={home.id} value={home.id}>
                  {home.name}
                </option>
              ))}
            </select>

            <button className="btn btn-success" disabled={saving} onClick={updateDevice}>
              {saving ? "Saving....":"Save"}
            </button>
          </div>
        )}

        <p>
          Status:
          {info.online ? " 🟢 Online" : " 🔴 Offline"}
        </p>
        {state && (
          <div className="card p-3 mb-3">
            <h5>Current State</h5>

            <p>💡 Light : <span className={
        state.lightState
        ? "text-success"
        : "text-danger"
    }>{state.lightState ? "ON" : "OFF"}</span></p>

            <p>🌀 Fan : <span className={
        state.fanState
        ? "text-success"
        : "text-danger"
    }>{state.fanState ? "ON" : "OFF"}
    </span></p>

            <p>📺 TV : <span className={
        state.tvState
        ? "text-success"
        : "text-danger"
    }>
      {state.tvState ? "ON" : "OFF"}
      </span></p>

            <p>🔌 Plug : <span className={
        state.plugState
        ? "text-success"
        : "text-danger"
    }>{state.plugState ? "ON" : "OFF"}
    </span></p>
          </div>
        )}

        <hr />

        
      </div>

      {permissions?.canControl && (
   <div className="card p-3 mb-3">

    <h5>Controls</h5>     
<div className="d-flex gap-2 flex-wrap">

    
    <button
    className={
        state.lightState
            ? "btn btn-success"
            : "btn btn-secondary"
    }
    onClick={() =>
        controlDevice("LIGHT")
    }
>
    💡 {
        state.lightState
            ? "ON"
            : "OFF"
    }
</button>

   
    <button
    className={
        state.fanState
            ? "btn btn-success"
            : "btn btn-secondary"
    }
    onClick={() =>
        controlDevice("FAN")
    }
>
    🌀 {
        state.fanState
            ? "ON"
            : "OFF"
    }
</button>

    <button
    className={
        state.tvState
            ? "btn btn-success"
            : "btn btn-secondary"
    }
    onClick={() =>
        controlDevice("TV")
    }
>
    📺 {
        state.tvState
            ? "ON"
            : "OFF"
    }
</button>

   
<button
    className={
        state.plugState
            ? "btn btn-success"
            : "btn btn-secondary"
    }
    onClick={() =>
        controlDevice("PLUG")
    }
>
    🔌 {
        state.plugState
            ? "ON"
            : "OFF"
    }
</button>

</div>
</div>
)}
     {permissions?.canShare && (
       <div className="card p-3 mb-3">

    <h5>
        Share Device
    </h5>

    <input
        className="form-control mb-2"
        placeholder="User Email"
        value={shareEmail}
        onChange={(e)=>
            setShareEmail(
                e.target.value
            )
        }
    />

    <select
        className="form-select mb-2"
        value={role}
        onChange={(e)=>
            setRole(
                e.target.value
            )
        }
    >

        <option value="VIEWER">
            VIEWER
        </option>

        <option value="EDITOR">
            EDITOR
        </option>

    </select>

    <button
    className="btn btn-primary"
    disabled={sharing}
    onClick={shareDevice}
>
    {sharing ? "Sharing..." : "Share"}
</button>

</div>
     )}
    </>
  );
}

export default DeviceDetailsPage;
