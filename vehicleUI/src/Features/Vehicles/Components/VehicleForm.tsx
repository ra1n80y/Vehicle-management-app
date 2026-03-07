import { useEffect, useState } from "react";
import type { Vehicle } from "../Types/Vehicle";

type Props = {
  onCreate: (vehicle: Omit<Vehicle, "id">) => void;
  onUpdate: (vehicle: Vehicle) => void;
  editingVehicle: Vehicle | null;
  cancelEdit: () => void;
};

export default function VehicleForm({
  onCreate,
  onUpdate,
  editingVehicle,
  cancelEdit,
}: Props) {
  const [name, setName] = useState("");
  const [model, setModel] = useState("");
  const [type, setType] = useState("");
  const [year, setYear] = useState("");

  useEffect(() => {
    if (editingVehicle) {
      setName(editingVehicle.name);
      setModel(editingVehicle.model);
      setType(editingVehicle.type);
      setYear(String(editingVehicle.year));
    }
  }, [editingVehicle]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (editingVehicle) {
      onUpdate({
        id: editingVehicle.id,
        name,
        model,
        type,
        year: Number(year),
      });
    } else {
      onCreate({
        name,
        model,
        type,
        year: Number(year),
      });
    }

    setName("");
    setModel("");
    setType("");
    setYear("");
  };

  return (
    <form className="mb-4" onSubmit={handleSubmit}>
      <div className="row g-2">
        <div className="col">
          <input
            className="form-control"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>

        <div className="col">
          <input
            className="form-control"
            placeholder="Model"
            value={model}
            onChange={(e) => setModel(e.target.value)}
          />
        </div>

        <div className="col">
          <input
            className="form-control"
            placeholder="Type"
            value={type}
            onChange={(e) => setType(e.target.value)}
          />
        </div>

        <div className="col">
          <input
            className="form-control"
            type="number"
            placeholder="Year"
            value={year}
            onChange={(e) => setYear(e.target.value)}
          />
        </div>

        <div className="col-auto">
          <button className="btn btn-success">
            {editingVehicle ? "Update" : "Add"}
          </button>

          {editingVehicle && (
            <button
              type="button"
              className="btn btn-secondary ms-2"
              onClick={cancelEdit}
            >
              Cancel
            </button>
          )}
        </div>
      </div>
    </form>
  );
}
