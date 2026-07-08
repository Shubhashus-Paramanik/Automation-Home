import { useEffect, useState } from "react";
import api from "../services/api";
import Navbar from "../components/Navbar";
import LoadingSpinner from "../components/LoadingSpinner";
import { toast } from "react-toastify";

function SharedDevicesPage() {
  const [devices, setDevices] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadDevices = async () => {
    try {
      const res = await api.get("/device-access");

      setDevices(res.data);
    } catch {
      toast.error("Unable to load shared devices");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDevices();
  }, []);

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <>
      {/* <Navbar /> */}

      <div className="container mt-4">
        <h2>Shared Devices</h2>

        {devices.length === 0 && (
          <div className="alert alert-info">No shared devices</div>
        )}

        <div className="row">
          {devices.map((device) => (
            <div key={device.id} className="col-md-4 mb-3">
              <div className="card p-3">
                <h5>{device.name}</h5>

                <p>Device ID: {device.deviceId}</p>

                <p>Status: {device.online ? "🟢 Online" : "🔴 Offline"}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default SharedDevicesPage;
