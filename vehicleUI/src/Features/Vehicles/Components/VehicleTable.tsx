// src/Features/Vehicles/Components/VehicleTable.tsx
import type { Vehicle } from "../Types/Vehicle";
import VehicleRow from "./VehicleRow";

type Props = {
  vehicles: Vehicle[];
  onDelete?: (vehicle: Vehicle) => void; // Made optional
  onEdit?: (vehicle: Vehicle) => void; // Made optional
};

export default function VehicleTable({ vehicles, onDelete, onEdit }: Props) {
  return (
    <table className="table table-striped">
      <thead className="table-dark">
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Model</th>
          <th>Type</th>
          <th>Year</th>
          <th>Actions</th>
        </tr>
      </thead>

      <tbody>
        {vehicles.map((v) => (
          <VehicleRow
            key={v.id}
            vehicle={v}
            onDelete={onDelete}
            onEdit={onEdit}
          />
        ))}
      </tbody>
    </table>
  );
}
