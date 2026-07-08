import { useEffect, useState } from "react";
import api from "../services/api";
import Navbar from "../components/Navbar";
import { toast } from "react-toastify";

function SchedulePage() {
  const [devices, setDevices] = useState([]);
  const [schedules, setSchedules] = useState([]);

  const [deviceId, setDeviceId] = useState("");
  const [time, setTime] = useState("");
  const [deviceType, setDeviceType] = useState("LIGHT");
  const [state, setState] = useState(true);
  const [repeatType, setRepeatType] = useState("DAILY");

  useEffect(() => {
    loadDevices();
    loadSchedules();
  }, []);

  const loadDevices = async () => {
  try {
    const res = await api.get("/devices");
    setDevices(res.data);
  } catch (err) {
    toast.error("Unable to load devices");
  }
};

  const loadSchedules = async () => {
    try {
      const res = await api.get("/schedules");

      setSchedules(res.data);
    } catch (err) {
      toast.error("Unable to load schedules");
    }
  };

  
  const createSchedule = async () => {
    if (!deviceId || !time) {
      toast.error("Please select device and time");
      return;
    }
    try {
      await api.post("/schedules", {
        deviceId,
        deviceType,
        state,
        scheduleTime: time,
        repeatType,
      });

      toast.success("Schedule Created");
      setDeviceId("");
      setDeviceType("LIGHT");
      setState(true);
      setRepeatType("DAILY");
      setTime("");

      loadSchedules();
    } catch {
      toast.error("Unable to create schedule");
    }
  };

  const deleteSchedule = async (id) => {
    try {
      await api.delete(`/schedules/${id}`);

      toast.success("Schedule Deleted");

      loadSchedules();
    } catch {
      toast.error("Unable to delete schedule");
    }
  };

  return (
    <>
      {/* <Navbar /> */}

      <div className="container mt-4">
        <h2>Schedules</h2>

        <div className="card p-3 mb-4">
          <select
            className="form-select mb-2"
            value={deviceId}
            onChange={(e) => setDeviceId(e.target.value)}
          >
            <option value="">Select Device</option>

            {devices.map((device) => (
              <option key={device.deviceId} value={device.deviceId}>
                {device.name}
              </option>
            ))}
          </select>

          <select
            className="form-select mb-2"
            value={deviceType}
            onChange={(e) => setDeviceType(e.target.value)}
          >
            <option value="LIGHT">LIGHT</option>

            <option value="FAN">FAN</option>

            <option value="TV">TV</option>

            <option value="PLUG">PLUG</option>
          </select>

          <select
            className="form-select mb-2"
            value={state}
            onChange={(e) => setState(e.target.value === "true")}
          >
            <option value={true}>ON</option>

            <option value={false}>OFF</option>
          </select>
          <select
            className="form-select mb-2"
            value={repeatType}
            onChange={(e) => setRepeatType(e.target.value)}
          >
            <option value="DAILY">DAILY</option>

            <option value="WEEKLY">WEEKLY</option>

            <option value="ONCE">ONCE</option>
          </select>

          <input
            type="time"
            className="form-control mb-2"
            value={time}
            onChange={(e) => setTime(e.target.value)}
          />

          <button className="btn btn-success" onClick={createSchedule}>
            Create Schedule
          </button>
        </div>

        {schedules.length === 0 && (
          <div className="alert alert-info">No schedules found</div>
        )}
        {schedules.map((schedule) => (
          <div key={schedule.id} className="card p-3 mb-2">
            {/* <h5>{schedule.device?.name} - {schedule.deviceType}</h5> */}
            <h5>{schedule.device?.name}</h5>

            {/* <p>
              Device Type:
              {schedule.deviceType}
            </p> */}

            <p>
              Device:
              {schedule.deviceType}
            </p>

            <p>
              State:
              {schedule.state ? "ON" : "OFF"}
            </p>

            <p>
              Time:
              {schedule.scheduleTime}
            </p>

            <p>
              Repeat:
              {schedule.repeatType}
            </p>

            <button
              className="btn btn-danger btn-sm"
              onClick={() => deleteSchedule(schedule.id)}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
    </>
  );
}

export default SchedulePage;
