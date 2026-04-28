import { NavLink } from "react-router-dom";
import "./Sidebar.css";
import { useAuth } from "../../Auth/Hooks/useAuth";
import { hasPermission } from "../../Auth/Utils/permissions";

const Sidebar = () => {
  const { user } = useAuth();
  const canViewAuditLogs = hasPermission(user, "AUDIT_READ");
  const canViewUsers = hasPermission(user, "USER_READ"); // <-- NEW

  return (
    <aside className="sidebar">
      <div className="sidebar__brand">
        <h2>Vehicle Admin</h2>
        <p>Management System</p>
      </div>

      <nav className="sidebar__nav">
        <NavLink
          to="/dashboard"
          className={({ isActive }) =>
            isActive ? "sidebar__link sidebar__link--active" : "sidebar__link"
          }
        >
          Dashboard
        </NavLink>

        <NavLink
          to="/vehicles"
          className={({ isActive }) =>
            isActive ? "sidebar__link sidebar__link--active" : "sidebar__link"
          }
        >
          Vehicles
        </NavLink>

        {/* Users link – only visible if user has USER_READ */}
        {canViewUsers && (
          <NavLink
            to="/users"
            className={({ isActive }) =>
              isActive ? "sidebar__link sidebar__link--active" : "sidebar__link"
            }
          >
            Users
          </NavLink>
        )}

        {canViewAuditLogs && (
          <NavLink
            to="/audit-logs"
            className={({ isActive }) =>
              isActive ? "sidebar__link sidebar__link--active" : "sidebar__link"
            }
          >
            Audit Logs
          </NavLink>
        )}
      </nav>
    </aside>
  );
};

export default Sidebar;
