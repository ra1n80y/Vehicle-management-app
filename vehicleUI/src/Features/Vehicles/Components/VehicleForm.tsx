import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import type { Vehicle } from "../Types/Vehicle";

type Props = {
  onCreate: (vehicle: Omit<Vehicle, "id">) => Promise<void>;
  onUpdate: (id: number, vehicle: Vehicle) => Promise<void>;
  editingVehicle: Vehicle | null;
  cancelEdit: () => void;
};

type FormData = {
  name: string;
  model: string;
  type: string;
  year: number;
};

export default function VehicleForm({
  onCreate,
  onUpdate,
  editingVehicle,
  cancelEdit,
}: Props) {
  const [loading, setLoading] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    setFocus,
    formState: { errors, isValid },
  } = useForm<FormData>({
    mode: "onChange",
  });

  useEffect(() => {
    if (editingVehicle) {
      reset({
        name: editingVehicle.name,
        model: editingVehicle.model,
        type: editingVehicle.type,
        year: editingVehicle.year,
      });
    } else {
      reset({
        name: "",
        model: "",
        type: "",
        year: undefined,
      });
    }

    // Focus the name input after reset
    setFocus("name");
  }, [editingVehicle, reset, setFocus]);

  const onSubmit = async (data: FormData) => {
    setLoading(true);
    try {
      if (editingVehicle) {
        await onUpdate(editingVehicle.id, {
          id: editingVehicle.id,
          ...data,
        });
      } else {
        await onCreate(data);
      }
      reset();
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="mb-4" onSubmit={handleSubmit(onSubmit)}>
      <div className="row g-2">
        <div className="col">
          <input
            className={`form-control ${errors.name ? "is-invalid" : ""}`}
            placeholder="Name"
            {...register("name", { required: "Name is required" })}
          />
          {errors.name && (
            <div className="invalid-feedback">{errors.name.message}</div>
          )}
        </div>

        <div className="col">
          <input
            className={`form-control ${errors.model ? "is-invalid" : ""}`}
            placeholder="Model"
            {...register("model", { required: "Model is required" })}
          />
          {errors.model && (
            <div className="invalid-feedback">{errors.model.message}</div>
          )}
        </div>

        <div className="col">
          <input
            className={`form-control ${errors.type ? "is-invalid" : ""}`}
            placeholder="Type"
            {...register("type", { required: "Type is required" })}
          />
          {errors.type && (
            <div className="invalid-feedback">{errors.type.message}</div>
          )}
        </div>

        <div className="col">
          <input
            type="number"
            className={`form-control ${errors.year ? "is-invalid" : ""}`}
            placeholder="Year"
            {...register("year", {
              required: "Year is required",
              valueAsNumber: true,
              min: { value: 1900, message: "Year must be after 1900" },
              max: {
                value: new Date().getFullYear(),
                message: "Year cannot be in the future",
              },
            })}
          />
          {errors.year && (
            <div className="invalid-feedback">{errors.year.message}</div>
          )}
        </div>

        <div className="col-auto">
          <button
            type="submit"
            className="btn btn-success"
            disabled={!isValid || loading}
          >
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
              disabled={loading}
            >
              Cancel
            </button>
          )}
        </div>
      </div>
    </form>
  );
}
