import { apiClient } from "../../../Shared/Services/apiClient";
import type { RoleResponseDTO } from "../Types/Role";

export const RoleService = {
  getAll: async (): Promise<RoleResponseDTO[]> => {
    return apiClient.get("/api/roles");
  },
};