import { useEffect, useState } from "react";
import type { Vehicle } from "./Types/Vehicle";
import {
  getVehicles,
  deleteVehicle,
  postVehicle,
  putVehicle,
} from "./Services/VehicleService";

import VehicleTable from "./Components/VehicleTable";
import VehicleForm from "./Components/VehicleForm";

export default function VehiclesPage() {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [editingVehicle, setEditingVehicle] = useState<Vehicle | null>(null);

  const loadVehicles = async () => {
    const data = await getVehicles();
    setVehicles(data);
  };

  useEffect(() => {
    loadVehicles();
  }, []);

  const handleDelete = async (id: number) => {
    await deleteVehicle(id);

    setVehicles((prev) => prev.filter((v) => v.id !== id));
  };

  const handleCreate = async (vehicle: Omit<Vehicle, "id">) => {
    const created = await postVehicle(vehicle);

    setVehicles((prev) => [...prev, created]);
  };

  const handleEdit = (vehicle: Vehicle) => {
    setEditingVehicle(vehicle);
  };

  const cancelEdit = () => {
    setEditingVehicle(null);
  };

  const handleUpdate = async (vehicle: Vehicle) => {
    await putVehicle(vehicle.id, vehicle);

    setVehicles((prev) => prev.map((v) => (v.id === vehicle.id ? vehicle : v)));

    setEditingVehicle(null);
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-3">Vehicles</h1>
      <VehicleForm
        onCreate={handleCreate}
        onUpdate={handleUpdate}
        editingVehicle={editingVehicle}
        cancelEdit={cancelEdit}
      />
      <VehicleTable
        vehicles={vehicles}
        onDelete={handleDelete}
        onEdit={handleEdit}
      />{" "}
    </div>
  );
}
