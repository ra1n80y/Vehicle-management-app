import { useEffect, useState, useRef } from "react";
import type { Vehicle } from "../Types/Vehicle";

type Props = {
  onCreate: (vehicle: Omit<Vehicle, "id">) => Promise<void>;
  onUpdate: (id: number, vehicle: Vehicle) => Promise<void>;
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
  const [loading, setLoading] = useState(false);
  const nameInputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (editingVehicle) {
      setName(editingVehicle.name);
      setModel(editingVehicle.model);
      setType(editingVehicle.type);
      setYear(String(editingVehicle.year));
    } else {
      setName("");
      setModel("");
      setType("");
      setYear("");
    }

    nameInputRef.current?.focus();
  }, [editingVehicle]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (editingVehicle) {
        await onUpdate(editingVehicle.id, {
          id: editingVehicle.id,
          name,
          model,
          type,
          year: Number(year),
        });
      } else {
        await onCreate({
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
    } finally {
      setLoading(false);
    }
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
            ref={nameInputRef}
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
          <button type="submit" className="btn btn-success" disabled={loading}>
            {loading
              ? editingVehicle
                ? "Updating..."
                : "Adding..."
              : editingVehicle
                ? "Update"
                : "Add"}
          </button>

          {editingVehicle && (
            <button
              type="button"
              className="btn btn-secondary ms-2"
              onClick={cancelEdit}
              disabled={loading} // prevent cancel mid-submit
            >
              Cancel
            </button>
          )}
        </div>
      </div>
    </form>
  );
}
