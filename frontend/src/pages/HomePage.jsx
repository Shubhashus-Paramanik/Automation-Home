import { useEffect, useState } from "react";
import api from "../services/api";
// import Navbar from "../components/Navbar";
import { toast } from "react-toastify";
import LoadingSpinner from "../components/LoadingSpinner";

function HomesPage() {
  const [homes, setHomes] = useState([]);
  const [name, setName] = useState("");
  const[loading, setLoading]=useState(true);

  useEffect(() => {
    api
      .get("/homes")
      .then((res) => {
        setHomes(res.data);
      })
      .catch((err) => {
        console.log(err);
      }).finally(()=>{
        setLoading(false);
      })
  }, []);
  if(loading){
    return <LoadingSpinner/>
  }

  const createHome = async () => {
    try {
      await api.post("/homes", {
        name,
      });
     toast.success("Home Created");
      window.location.reload();
      //remove letter
    } catch (err) {
      // console.log(err);
      toast.error("Unable to create Home");
    }
  };

  return (
    <>
      {/* <Navbar /> */}
      <div className="container mt-4">
        <h2>My Homes</h2>

        <div className="row">
          {homes.map((home) => (
            <div key={home.id} className="col-md-4 mb-3">
              <div className="card p-3">
                <h5>{home.name}</h5>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="mb-3">
        <input
          className="form-control"
          placeholder="Home Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
      </div>

      <button className="btn btn-success" onClick={createHome}>
        Add Home
      </button>
    </>
  );
}

export default HomesPage;
