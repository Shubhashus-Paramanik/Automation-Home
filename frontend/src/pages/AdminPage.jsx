import { useEffect, useState } from "react";
import api from "../services/api";
import Navbar from "../components/Navbar";
import LoadingSpinner from "../components/LoadingSpinner";
import { toast } from "react-toastify";

function AdminPage() {

    const [stats, setStats] = useState(null);

    useEffect(() => {
        loadStats();
    }, []);

    const loadStats = async () => {

        try {

            const res =
                await api.get("/admin/stats");

            setStats(res.data);

        } catch {

            toast.error(
                "Unable to load admin stats"
            );

        }
    };

    if (!stats) {
        return <LoadingSpinner />;
    }

    return (
        <>
            {/* <Navbar /> */}

            <div className="container mt-4">

                <h2 className="mb-4">
                    Admin Dashboard
                </h2>

                <div className="row">

                    <div className="col-md-4 mb-3">
                        <div className="card p-4 text-center">
                            <h1>{stats.totalUsers}</h1>
                            <p>Total Users</p>
                        </div>
                    </div>

                    <div className="col-md-4 mb-3">
                        <div className="card p-4 text-center">
                            <h1>{stats.totalHomes}</h1>
                            <p>Total Homes</p>
                        </div>
                    </div>

                    <div className="col-md-4 mb-3">
                        <div className="card p-4 text-center">
                            <h1>{stats.totalDevices}</h1>
                            <p>Total Devices</p>
                        </div>
                    </div>

                    <div className="col-md-6 mb-3">
                        <div className="card p-4 text-center">
                            <h1>{stats.totalSchedules}</h1>
                            <p>Total Schedules</p>
                        </div>
                    </div>

                    <div className="col-md-6 mb-3">
                        <div className="card p-4 text-center">
                            <h1>{stats.totalNotifications}</h1>
                            <p>Total Notifications</p>
                        </div>
                    </div>

                </div>

            </div>
        </>
    );
}

export default AdminPage;