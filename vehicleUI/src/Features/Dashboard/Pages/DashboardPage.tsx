import "./DashboardPage.css";

const DashboardPage = () => {
  return (
    <section className="dashboard-page">
      <div className="dashboard-page__header">
        <h2>Dashboard</h2>
        <p>
          Overview of the Vehicle Management System. Use the sidebar to navigate
          between sections.
        </p>
      </div>

      <div className="dashboard-page__grid">
        <div className="dashboard-card">
          <h3>Vehicles</h3>
          <p>
            Create, view, edit, and delete vehicle records. Ownership and
            permission checks are enforced based on your role.
          </p>
        </div>

        <div className="dashboard-card">
          <h3>Users</h3>
          <p>
            Manage system users, assign roles, and control account status.
            Role‑based access ensures only authorised admins can make changes.
          </p>
        </div>

        <div className="dashboard-card">
          <h3>Audit Logs</h3>
          <p>
            View a secure, append‑only history of all successful mutations
            across vehicles, users, and roles. Click any row for full details.
          </p>
        </div>

        <div className="dashboard-card">
          <h3>Security Model</h3>
          <p>
            Every action is protected by Spring Security on the backend and
            conditional rendering on the frontend. JWT tokens carry roles and
            permissions for a seamless RBAC experience.
          </p>
        </div>
      </div>
    </section>
  );
};

export default DashboardPage;
