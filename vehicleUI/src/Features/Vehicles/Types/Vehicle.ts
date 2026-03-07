export interface Vehicle {
  id: number;
  name: string;
  model: string;
  type: string;
  year: number;
}

export type VehicleCreate = Omit<Vehicle, "id">;