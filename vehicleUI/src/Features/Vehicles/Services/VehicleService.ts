import { apiClient } from "../../../Shared/Services/apiClient";
import type { Vehicle } from "../Types/Vehicle";

const VEHICLES_ENDPOINT = "/api/vehicles";

export const VehicleService = {
  getAll: async (): Promise<Vehicle[]> => {
    return apiClient.get<Vehicle[]>(VEHICLES_ENDPOINT);
  },

  create: async (vehicle: Omit<Vehicle, "id">): Promise<Vehicle> => {
    return apiClient.post<Vehicle, Omit<Vehicle, "id">>(
      VEHICLES_ENDPOINT,
      vehicle
    );
  },

  update: async (
    id: number,
    vehicle: Omit<Vehicle, "id">
  ): Promise<Vehicle> => {
    return apiClient.put<Vehicle, Omit<Vehicle, "id">>(
      `${VEHICLES_ENDPOINT}/${id}`,
      vehicle
    );
  },

  delete: async (id: number): Promise<void> => {
    return apiClient.delete<void>(`${VEHICLES_ENDPOINT}/${id}`);
  },
};