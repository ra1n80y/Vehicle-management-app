import type { Vehicle } from "../Types/Vehicle";

type Props = {
  vehicle: Vehicle;
  onDelete: (id: number) => void;
  onEdit: (vehicle: Vehicle) => void;
};

export default function VehicleRow({ vehicle, onDelete, onEdit }: Props) {
  return (
    <tr>
      <td>{vehicle.id}</td>
      <td>{vehicle.name}</td>
      <td>{vehicle.model}</td>
      <td>{vehicle.type}</td>
      <td>{vehicle.year}</td>

      <td>
        <button
          className="btn btn-primary btn-sm me-2"
          onClick={() => onEdit(vehicle)}
        >
          Edit
        </button>

        <button
          className="btn btn-danger btn-sm"
          onClick={() => onDelete(vehicle.id)}
        >
          Delete
        </button>
      </td>
    </tr>
  );
}
