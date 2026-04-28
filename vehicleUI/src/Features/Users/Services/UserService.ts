import { apiClient } from "../../../Shared/Services/apiClient";
import type {
  UserResponseDTO,
  UserCreateDTO,
  UserUpdateDTO,
  UserPatchDTO,
} from "../Types/User";

const BASE_URL = "/api/users";

export const UserService = {
  getAll: async (): Promise<UserResponseDTO[]> => {
    return apiClient.get(BASE_URL);
  },

  getById: async (id: number): Promise<UserResponseDTO> => {
    return apiClient.get(`${BASE_URL}/${id}`);
  },

  create: async (data: UserCreateDTO): Promise<UserResponseDTO> => {
    return apiClient.post(BASE_URL, data);
  },

  update: async (id: number, data: UserUpdateDTO): Promise<UserResponseDTO> => {
    return apiClient.put(`${BASE_URL}/${id}`, data);
  },

  patch: async (id: number, data: UserPatchDTO): Promise<UserResponseDTO> => {
    return apiClient.patch(`${BASE_URL}/${id}`, data);
  },

  delete: async (id: number): Promise<void> => {
    return apiClient.delete(`${BASE_URL}/${id}`);
  },
};