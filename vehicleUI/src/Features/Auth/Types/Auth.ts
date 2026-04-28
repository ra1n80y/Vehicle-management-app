export type LoginRequest = {
  username: string;
  password: string;
};

export type LoginResponse = {
  token: string;
};

export type JwtPayload = {
  sub?: string;
  exp?: number;
  roles?: string[];
  permissions?: string[];
};

export type AuthUser = {
  username: string;
  roles: string[];
  permissions: string[];
};

export type AuthContextType = {
  token: string | null;
  user: AuthUser | null;
  isAuthenticated: boolean;
  isInitializing: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  logout: () => void;
};