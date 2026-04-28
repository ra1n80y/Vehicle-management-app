import { createBrowserRouter, Navigate } from "react-router-dom";
import LoginPage from "../Features/Auth/Pages/LoginPage";
import ProtectedRoute from "../Features/Auth/Components/ProtectedRoute";
import AppShell from "../Features/Dashboard/Components/AppShell";
import DashboardPage from "../Features/Dashboard/Pages/DashboardPage";
import VehiclesPage from "../Features/Vehicles/Pages/VehiclesPage";
import AuditLogsPage from "../Features/AuditLogs/Pages/AuditLogsPage";
import UsersPage from "../Features/Users/Pages/UsersPage";

export const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    element: <ProtectedRoute />,
    children: [
      {
        element: <AppShell />,
        children: [
          {
            index: true,
            element: <Navigate to="/dashboard" replace />,
          },
          {
            path: "/dashboard",
            element: <DashboardPage />,
          },
          {
            path: "/vehicles",
            element: <VehiclesPage />,
          },
          {
            path: "/audit-logs",
            element: <AuditLogsPage />,
          },
          {
            path: "/users",
            element: <UsersPage />,
          },
        ],
      },
    ],
  },
  {
    path: "*",
    element: <Navigate to="/login" replace />,
  },
]);
