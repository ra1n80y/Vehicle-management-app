import type { AuthUser, JwtPayload } from "../Types/Auth";

function base64UrlDecode(value: string): string {
  const base64 = value.replace(/-/g, "+").replace(/_/g, "/");
  const padded = base64.padEnd(base64.length + ((4 - (base64.length % 4)) % 4), "=");
  return atob(padded);
}

export const decodeJwt = (token: string): JwtPayload | null => {
  try {
    const parts = token.split(".");
    if (parts.length !== 3) {
      return null;
    }

    const payload = base64UrlDecode(parts[1]);
    return JSON.parse(payload) as JwtPayload;
  } catch {
    return null;
  }
};

export const isTokenExpired = (token: string): boolean => {
  const payload = decodeJwt(token);

  if (!payload?.exp) {
    return true;
  }

  const nowInSeconds = Math.floor(Date.now() / 1000);
  return payload.exp < nowInSeconds;
};

export const getUserFromToken = (token: string): AuthUser | null => {
  const payload = decodeJwt(token);

  if (!payload?.sub) {
    return null;
  }

  return {
    username: payload.sub,
    roles: Array.isArray(payload.roles) ? payload.roles : [],
    permissions: Array.isArray(payload.permissions) ? payload.permissions : [],
  };
};