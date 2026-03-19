import { createBrowserRouter, Navigate } from "react-router-dom";
import LoginPage from "../Features/Auth/Pages/LoginPage";
import ProtectedRoute from "../Features/Auth/Components/ProtectedRoute";
import VehiclesPage from "../Features/Vehicles/Pages/VehiclesPage";

export const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    element: <ProtectedRoute />,
    children: [
      {
        path: "/vehicles",
        element: <VehiclesPage />,
      },
    ],
  },
  {
    path: "*",
    element: <Navigate to="/login" replace />,
  },
]);
