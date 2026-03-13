import { useEffect, useState } from "react";
import type { Vehicle } from "./Types/Vehicle";
import {
  getVehicles,
  deleteVehicle,
  putVehicle,
  postVehicle,
} from "./Services/VehicleService";

import VehicleTable from "./Components/VehicleTable";
import Button from "react-bootstrap/Button";
import ToastNotification from "../../Shared/Components/ToastNotification";
import useToast from "../../Shared/Hooks/useToast";
import CommonModal from "../../Shared/Components/CommonModal";
import VehicleForm from "./Components/VehicleForm";

export default function VehiclesPage() {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);

  const [showModal, setShowModal] = useState(false);
  const [selectedVehicle, setSelectedVehicle] = useState<Vehicle | null>(null);

  const [showConfirm, setShowConfirm] = useState(false);
  const [vehicleToDelete, setVehicleToDelete] = useState<Vehicle | null>(null);

  const { message, show, triggerToast, closeToast } = useToast();

  const loadVehicles = async () => {
    const data = await getVehicles();
    setVehicles(data);
  };

  useEffect(() => {
    loadVehicles();
  }, []);

  const handleCreate = async (vehicle: Omit<Vehicle, "id">) => {
    const createdVehicle = await postVehicle(vehicle);

    setVehicles((prev) => [...prev, createdVehicle]);
    setShowModal(false);

    triggerToast("Vehicle added successfully");
  };

  const handleDeleteClick = (vehicle: Vehicle) => {
    setVehicleToDelete(vehicle);
    setShowConfirm(true);
  };

  const confirmDelete = async () => {
    if (!vehicleToDelete) return;

    await deleteVehicle(vehicleToDelete.id);

    setVehicles((prev) => prev.filter((v) => v.id !== vehicleToDelete.id));

    triggerToast("Vehicle deleted successfully");

    setVehicleToDelete(null);
    setShowConfirm(false);
  };

  const handleEdit = (vehicle: Vehicle) => {
    setSelectedVehicle(vehicle);
    setShowModal(true);
  };

  const handleUpdate = async (vehicle: Vehicle) => {
    await putVehicle(vehicle.id, vehicle);

    setVehicles((prev) => prev.map((v) => (v.id === vehicle.id ? vehicle : v)));

    setShowModal(false);

    triggerToast("Vehicle updated successfully");
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedVehicle(null);
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-3">Vehicles</h1>

      <Button
        className="mb-2"
        variant="primary"
        onClick={() => {
          setSelectedVehicle(null);
          setShowModal(true);
        }}
      >
        Add Vehicle
      </Button>

      <VehicleTable
        vehicles={vehicles}
        onDelete={handleDeleteClick}
        onEdit={handleEdit}
      />

      <CommonModal
        show={showModal}
        title={selectedVehicle ? "Edit Vehicle" : "Add Vehicle"}
        onClose={handleCloseModal}
      >
        <VehicleForm
          editingVehicle={selectedVehicle}
          onCreate={handleCreate}
          onUpdate={async (_id, updatedVehicle) => {
            await handleUpdate(updatedVehicle);
          }}
          cancelEdit={handleCloseModal}
        />
      </CommonModal>

      <CommonModal
        show={showConfirm}
        title="Delete Vehicle"
        onClose={() => setShowConfirm(false)}
      >
        <p>
          Are you sure you want to delete{" "}
          <strong>{vehicleToDelete?.name}</strong>?
        </p>

        <div className="d-flex justify-content-end gap-2">
          <button
            className="btn btn-secondary"
            onClick={() => setShowConfirm(false)}
          >
            Cancel
          </button>

          <button className="btn btn-danger" onClick={confirmDelete}>
            Delete
          </button>
        </div>
      </CommonModal>

      <ToastNotification message={message} show={show} onClose={closeToast} />
    </div>
  );
}
