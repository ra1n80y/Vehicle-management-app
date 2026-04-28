export type AuditAction = "CREATE" | "UPDATE" | "PATCH" | "DELETE";

export type AuditLog = {
  id: number;
  action: AuditAction;
  resourceType: string;
  resourceId: number;
  performedBy: string;
  performedAt: string;
};