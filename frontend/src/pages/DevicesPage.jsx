import { useEffect, useState } from "react";
import api from "../services/api";
import DeviceCard from "../components/DeviceCard";
// import Navbar from "../components/Navbar";
import { toast } from "react-toastify";
import LoadingSpinner from "../components/LoadingSpinner";
import { connectSocket, disconnectSocket } from "../services/socket";

function DevicesPage() {
  const [devices, setDevices] = useState([]);
  const [homes, setHomes] = useState([]);

  const [name, setName] = useState("");
  const [deviceId, setDeviceId] = useState("");
  const [homeId, setHomeId] = useState("");

  const [search, setSearch] = useState("");
  const [selectedHome, setSelectedHome] = useState("");
  const [status, setStatus] = useState("ALL");

  const [loading, setLoading] = useState(true);

  const loadDevices = async () => {
    try {
      const res = await api.get("/devices");
      setDevices(res.data);
    } catch (err) {
      toast.error("Unable to load devices");
    }
  };

  const loadHomes = async () => {
    try {
      const res = await api.get("/homes");
      setHomes(res.data);
    } catch (err) {
      toast.error("Unable to load homes");
    }
  };

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);

      await Promise.all([loadDevices(), loadHomes()]);

      setLoading(false);
    };

    loadData();
  }, []);
  
  // useEffect(() => {
  //   if (devices.length === 0) return;

  //   console.log("DEVICES", devices);

  //   devices.forEach((device) => {
  //     console.log("CONNECTING TO", device.deviceId);

  //     connectSocket(
  //       device.deviceId,
  //       (message) => {
  //         console.log("BEFORE");
  //         console.log(devices);

  //       setDevices(prev => {
  //   const updated = prev.map(d => {
  //       if(d.deviceId === message.deviceId){

  //           console.log("OLD", d);

  //           const newDevice = {
  //               ...d,
  //               lightState: message.lightState,
  //               fanState: message.fanState,
  //               tvState: message.tvState,
  //               plugState: message.plugState,
  //               online: message.online,
  //           };

  //           console.log("NEW", newDevice);

  //           return newDevice;
  //       }

  //       return d;
  //   });

  //   return updated;
  //         });
         
  //       },
  //     );
  //   });
  // //   return () => {
  // //   disconnectSocket();
  // // };
  // }, []);

  useEffect(() => {

    if (devices.length === 0) return;

    devices.forEach(device => {

        connectSocket(
            device.deviceId,
            message => {

                setDevices(prev =>
                    prev.map(d =>
                        d.deviceId === message.deviceId
                            ? {
                                ...d,
                                lightState: message.lightState,
                                fanState: message.fanState,
                                tvState: message.tvState,
                                plugState: message.plugState,
                                online: message.online
                              }
                            : d
                    )
                );
            }
        );
    });

}, [devices.length]);

  const createDevice = async () => {
    if (!name || !deviceId || !homeId) {
      toast.warning("Please fill all fields");
      return;
    }
    try {
      await api.post("/devices", {
        name,
        deviceId,
        homeId,
      });

      toast.success("Device Created");

      setName("");
      setDeviceId("");
      setHomeId("");

      await loadDevices();
    } catch (err) {
      toast.error("Unable to create device");
    }
  };

  const filteredDevices = devices.filter((device) => {
    const matchSearch = device.name
      .toLowerCase()
      .includes(search.toLowerCase());

    const matchHome =
      selectedHome === "" || String(device.home?.id) === selectedHome;

    const matchStatus =
      status === "ALL" ||
      (status === "ONLINE" && device.online) ||
      (status === "OFFLINE" && !device.online);

    return matchSearch && matchHome && matchStatus;
  });

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <>
      {/* <Navbar /> */}

      <div className="container mt-4">
        <h2 className="mb-4">Devices</h2>

        {/* Filters */}

        <div className="card p-3 mb-4">
          <div className="row">
            <div className="col-md-4">
              <input
                className="form-control"
                placeholder="Search Device"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>

            <div className="col-md-4">
              <select
                className="form-select"
                value={selectedHome}
                onChange={(e) => setSelectedHome(e.target.value)}
              >
                <option value="">All Homes</option>

                {homes.map((home) => (
                  <option key={home.id} value={home.id}>
                    {home.name}
                  </option>
                ))}
              </select>
            </div>

            <div className="col-md-4">
              <select
                className="form-select"
                value={status}
                onChange={(e) => setStatus(e.target.value)}
              >
                <option value="ALL">All Devices</option>
                <option value="ONLINE">Online</option>
                <option value="OFFLINE">Offline</option>
              </select>
            </div>
          </div>
        </div>

        {/* Create Device */}

        <div className="card p-3 mb-4">
          <h4>Create Device</h4>

          <input
            className="form-control mb-2"
            placeholder="Device Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />

          <input
            className="form-control mb-2"
            placeholder="Device ID"
            value={deviceId}
            onChange={(e) => setDeviceId(e.target.value)}
          />

          <select
            className="form-select mb-2"
            value={homeId}
            onChange={(e) => setHomeId(e.target.value)}
          >
            <option value="">Select Home</option>

            {homes.map((home) => (
              <option key={home.id} value={home.id}>
                {home.name}
              </option>
            ))}
          </select>

          <button className="btn btn-success" onClick={createDevice}>
            Create Device
          </button>
        </div>

        {/* Device List */}

        <div className="row">
          {filteredDevices.length === 0 && (
            <div className="alert alert-warning">No device found.</div>
          )}

          {filteredDevices.map((device) => (
            <div key={device.id} className="col-md-4 mb-3">
              <DeviceCard device={device} />
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default DevicesPage;
