import { apiClient } from "../../../Shared/Services/apiClient";
import type { AuditLog } from "../Types/AuditLog";

export const AuditLogService = {
  async getAuditLogs(): Promise<AuditLog[]> {
    return apiClient.get<AuditLog[]>("/api/audit-logs");
  },

  async getAuditLogById(id: number): Promise<AuditLog> {
    return apiClient.get<AuditLog>(`/api/audit-logs/${id}`);
  },
};