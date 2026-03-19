import { apiClient } from "../../../Shared/Services/apiClient";
import type { Vehicle } from "../Types/Vehicle";

const VEHICLES_ENDPOINT = "/api/vehicles";

export const getVehicles = async (): Promise<Vehicle[]> => {
  return apiClient.get<Vehicle[]>(VEHICLES_ENDPOINT);
};

export const postVehicle = async (
  vehicle: Omit<Vehicle, "id">
): Promise<Vehicle> => {
  return apiClient.post<Vehicle, Omit<Vehicle, "id">>(
    VEHICLES_ENDPOINT,
    vehicle
  );
};

export const updateVehicle = async (
  id: number,
  vehicle: Omit<Vehicle, "id">
): Promise<Vehicle> => {
  return apiClient.put<Vehicle, Omit<Vehicle, "id">>(
    `${VEHICLES_ENDPOINT}/${id}`,
    vehicle
  );
};

export const deleteVehicle = async (id: number): Promise<void> => {
  return apiClient.delete<void>(`${VEHICLES_ENDPOINT}/${id}`);
};