import { useEffect, useState } from "react";
import type { Vehicle } from "../Types/Vehicle";
import { VehicleService } from "../Services/VehicleService";
import VehicleTable from "../Components/VehicleTable";
import Button from "react-bootstrap/Button";
import ToastNotification from "../../../Shared/Components/ToastNotification";
import useToast from "../../../Shared/Hooks/useToast";
import CommonModal from "../../../Shared/Components/CommonModal";
import VehicleForm from "../Components/VehicleForm";
import { useAuth } from "../../Auth/Hooks/useAuth";
import { hasPermission } from "../../Auth/Utils/permissions";

export default function VehiclesPage() {
  const { user } = useAuth();

  const canCreate = hasPermission(user, "VEHICLE_CREATE");
  const canUpdate = hasPermission(user, "VEHICLE_UPDATE");
  const canDelete = hasPermission(user, "VEHICLE_DELETE");

  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  const [showModal, setShowModal] = useState(false);
  const [selectedVehicle, setSelectedVehicle] = useState<Vehicle | null>(null);

  const [showConfirm, setShowConfirm] = useState(false);
  const [vehicleToDelete, setVehicleToDelete] = useState<Vehicle | null>(null);

  const { message, show, triggerToast, closeToast } = useToast();

  const loadVehicles = async () => {
    try {
      setIsLoading(true);
      const data = await VehicleService.getAll();
      setVehicles(data);
    } catch (error) {
      const errorMessage =
        error instanceof Error ? error.message : "Failed to load vehicles";
      triggerToast(errorMessage);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadVehicles();
  }, []);

  const handleCreate = async (vehicle: Omit<Vehicle, "id">) => {
    try {
      const createdVehicle = await VehicleService.create(vehicle);
      setVehicles((prev) => [...prev, createdVehicle]);
      setShowModal(false);
      setSelectedVehicle(null);
      triggerToast("Vehicle added successfully");
    } catch (error) {
      const errorMessage =
        error instanceof Error ? error.message : "Failed to create vehicle";
      triggerToast(errorMessage);
    }
  };

  const handleDeleteClick = (vehicle: Vehicle) => {
    setVehicleToDelete(vehicle);
    setShowConfirm(true);
  };

  const confirmDelete = async () => {
    if (!vehicleToDelete) return;
    try {
      await VehicleService.delete(vehicleToDelete.id);
      setVehicles((prev) => prev.filter((v) => v.id !== vehicleToDelete.id));
      triggerToast("Vehicle deleted successfully");
      setVehicleToDelete(null);
      setShowConfirm(false);
    } catch (error) {
      const errorMessage =
        error instanceof Error ? error.message : "Failed to delete vehicle";
      triggerToast(errorMessage);
    }
  };

  const handleEdit = (vehicle: Vehicle) => {
    setSelectedVehicle(vehicle);
    setShowModal(true);
  };

  const handleUpdate = async (vehicle: Vehicle) => {
    try {
      const updated = await VehicleService.update(vehicle.id, {
        name: vehicle.name,
        model: vehicle.model,
        type: vehicle.type,
        year: vehicle.year,
      });
      setVehicles((prev) =>
        prev.map((v) => (v.id === updated.id ? updated : v)),
      );
      setShowModal(false);
      setSelectedVehicle(null);
      triggerToast("Vehicle updated successfully");
    } catch (error) {
      const errorMessage =
        error instanceof Error ? error.message : "Failed to update vehicle";
      triggerToast(errorMessage);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedVehicle(null);
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-3">Vehicles</h1>

      {canCreate && (
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
      )}

      {isLoading ? (
        <p>Loading vehicles...</p>
      ) : (
        <VehicleTable
          vehicles={vehicles}
          onDelete={canDelete ? handleDeleteClick : undefined}
          onEdit={canUpdate ? handleEdit : undefined}
        />
      )}

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
