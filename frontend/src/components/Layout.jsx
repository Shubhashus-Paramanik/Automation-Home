import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

function Layout() {
  return (
    <>
      <Navbar />

      <div className="container-fluid mt-4">
        <Outlet />
      </div>
    </>
  );
}

export default Layout;