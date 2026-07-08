import api from "../services/api";
import {useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import {connectSocket, disconnectSocket} from "../services/socket";


function DeviceCard({ device }) {
  const [currentDevice, setCurrentDevice] = useState(device);

  useEffect(() => {
    setCurrentDevice(device);
  }, [device]);

  const updateDevice = async (newState) => {
    try {
      const res = await api.post("/devices/control", {
        deviceId: currentDevice.deviceId,
        ...newState,
      });

      setCurrentDevice((prev) => ({
        ...prev,
        ...newState,
        ...res.data,
        online: res.data.online ?? prev.online,
      }));

      toast.success("Device Updated");
    } catch (err) {
      console.log(err);
      toast.error("Failed to update device");
    }
// try {
//         setCurrentDevice(prev => ({
//             ...prev,
//             ...newState,
//         }));

//         await api.post("/devices/control", {
//             deviceId: currentDevice.deviceId,
//             ...newState,
//         });

//         toast.success("Device Updated");
//     } catch (err) {
//         toast.error("Failed to update device");
//     }
  };

  const deleteDevice = async () => {
    const ok = window.confirm("Delete this device?");

    if (!ok) {
      return;
    }

    try {
      await api.delete(`/devices/${device.deviceId}`);

      // window.location.reload();
      toast.success("Device Deleted");
    } catch (err) {
      // console.log(err);
      toast.error("Delete Failed");
    }
  };
  const safeState = {
    lightState: currentDevice.lightState ?? false,
    fanState: currentDevice.fanState ?? false,
    tvState: currentDevice.tvState ?? false,
    plugState: currentDevice.plugState ?? false,
  };

  return (
    <div className="card p-3 shadow-sm">
      <h5>
        <Link
          to={`/devices/${currentDevice.deviceId}`}
          className="text-decoration-none"
        >
          {currentDevice.name}
        </Link>
      </h5>
      <p>
        <strong>ID:</strong> {currentDevice.deviceId}
      </p>
      {currentDevice.online ? (
        <span className="badge bg-success">Online</span>
      ) : (
        <span className="badge bg-danger">Offline</span>
      )}
      <hr />
      <p>
        Light:
        {safeState.lightState ? " ON" : " OFF"}
      </p>
      <button
        className="btn btn-primary btn-sm mb-2"
        onClick={() =>
          updateDevice({
            lightState: !currentDevice.lightState,
            fanState: currentDevice.fanState,
            tvState: currentDevice.tvState,
            plugState: currentDevice.plugState,
          })
        }
      >
        Toggle Light
      </button>
      <p>
        Fan:
        {currentDevice.fanState ? " ON" : " OFF"}
      </p>
      <button
        className="btn btn-primary btn-sm mb-2"
        onClick={() =>
          updateDevice({
            lightState: currentDevice.lightState,
            fanState: !currentDevice.fanState,
            tvState: currentDevice.tvState,
            plugState: currentDevice.plugState,
          })
        }
      >
        Toggle Fan
      </button>
      <p>
        Tv:
        {currentDevice.tvState ? " ON" : " OFF"}
      </p>
      <button
        className="btn btn-primary btn-sm mb-2"
        onClick={() =>
          updateDevice({
            lightState: currentDevice.lightState,
            fanState: currentDevice.fanState,
            tvState: !currentDevice.tvState,
            plugState: currentDevice.plugState,
          })
        }
      >
        Toggle Tv
      </button>

      <p>Plug :{currentDevice.plugState ? " ON" : " OFF"}</p>

      <button
        className="btn btn-primary btn-sm"
        onClick={() =>
          updateDevice({
            lightState: currentDevice.lightState,
            fanState: currentDevice.fanState,
            tvState: currentDevice.tvState,
            plugState: !currentDevice.plugState,
          })
        }
      >
        Toggle Plug
      </button>
     
<div className="mt-3 d-flex gap-2">
  <Link className="btn btn-outline-primary btn-sm mt-2 me-2" to={`/devices/${device.deviceId}`}>
  view Details</Link>
  <button className="btn btn-danger btn-sm mt-2" onClick={deleteDevice}>
    Delete Device
  </button>
</div>
    
    </div>
  );
}

export default DeviceCard;
