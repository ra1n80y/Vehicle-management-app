import { useNavigate } from "react-router-dom";
import { useAuth } from "../../Auth/Hooks/useAuth";
import "./Topbar.css";

const Topbar = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true });
  };

  return (
    <header className="topbar">
      <div>
        <h1 className="topbar__title">Admin Dashboard</h1>
        <p className="topbar__subtitle">Vehicle Management System</p>
      </div>

      <div className="topbar__actions">
        <div className="topbar__user">
          <span className="topbar__label">Signed in as</span>
          <span className="topbar__username">
            {user?.username ?? "Authenticated User"}
          </span>
        </div>

        <button className="topbar__logout-btn" onClick={handleLogout}>
          Logout
        </button>
      </div>
    </header>
  );
};

export default Topbar;
