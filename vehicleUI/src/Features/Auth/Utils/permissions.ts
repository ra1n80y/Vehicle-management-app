import type { AuthUser } from "../Types/Auth";

export const hasPermission = (
  user: AuthUser | null,
  permission: string,
): boolean => {
  return !!user && user.permissions.includes(permission);
};