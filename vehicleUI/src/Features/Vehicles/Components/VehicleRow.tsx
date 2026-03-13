import Button from "react-bootstrap/esm/Button";
import type { Vehicle } from "../Types/Vehicle";

type Props = {
  vehicle: Vehicle;
  onDelete: (vehicle: Vehicle) => void;
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
        <Button
          style={{ marginRight: "5px" }}
          variant="warning"
          size="sm"
          onClick={() => onEdit(vehicle)}
        >
          Edit
        </Button>

        <Button variant="danger" size="sm" onClick={() => onDelete(vehicle)}>
          Delete
        </Button>
      </td>
    </tr>
  );
}
