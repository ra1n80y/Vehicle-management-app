import { apiClient } from "../../../Shared/Services/apiClient";
import type { LoginRequest, LoginResponse } from "../Types/Auth";

export const AuthService = {
  login: async (credentials: LoginRequest): Promise<LoginResponse> => {
    return apiClient.post<LoginResponse, LoginRequest>(
      "/api/auth/login",
      credentials,
      false
    );
  },
};