import {useEffect, useState} from 'react'
import api from '../services/api'
import DeviceCard from '../components/DeviceCard';
import LoadingSpinner from "../components/LoadingSpinner";
import {connectSocket, disconnectSocket} from "../services/socket";

function Dashboard() {
    const [stats, setStats]=useState(null);

 useEffect(() => {

    api.get("/dashboard")
      .then(res => {
        setStats(res.data);
      });

  }, []);

useEffect(() => {

    if(!stats?.recentDevices?.length)
        return;

    stats.recentDevices.forEach(device => {

        connectSocket(
            device.deviceId,

            message => {

                setStats(prev => ({

                    ...prev,

                    recentDevices:
                        prev.recentDevices.map(d =>

                            d.deviceId === message.deviceId
                                ? {
                                      ...d,
                                      lightState:
                                          message.lightState,
                                      fanState:
                                          message.fanState,
                                      tvState:
                                          message.tvState,
                                      plugState:
                                          message.plugState,
                                      online:
                                          message.online,
                                  }
                                : d
                        )
                }));
            }
        );
    });

    return () => {
        disconnectSocket();
    };
}, [stats?.recentDevices?.length]);

 if(!stats){
    return <LoadingSpinner />;
  }
 return (
    <>
      {/* <Navbar /> */}

      <div className="container mt-4">
      <hr />
 
     <h3 className="mb-3">
        Recent Devices
     </h3>
        <div className="row">

          <div className="col-md-3">
            <div className="card p-3 text-center">
              <h3>{stats.totalHomes}</h3>
              <p>Total Homes</p>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card p-3 text-center">
              <h3>{stats.totalDevices}</h3>
              <p>Total Devices</p>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card p-3 text-center">
              <h3>{stats.onlineDevices}</h3>
              <p>Online</p>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card p-3 text-center">
              <h3>{stats.offlineDevices}</h3>
              <p>Offline</p>
            </div>
          </div>

        </div>

      </div>

      <hr className="my-4"/>

<h3>Recent Devices</h3>

<div className="row">

{stats.recentDevices?.map(device => (

  <div
    key={device.deviceId}
    className="col-md-4 mb-3"
  >

    {/* <div className="card p-3">

      <h5>{device.name}</h5>

      <p>{device.deviceId}</p>

      <p>
        {device.online
          ? "🟢 Online"
          : "🔴 Offline"}
      </p>

    </div> */}
    <DeviceCard device={device}/>

  </div>
  

))}

</div>

    </>
  );
}

export default Dashboard;