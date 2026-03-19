export type LoginRequest = {
  username: string;
  password: string;
};

export type LoginResponse = {
  token: string;
};

export type JwtPayload = {
  sub: string;
  exp: number;
  iat?: number;
  roles?: string[];
};

export type AuthUser = {
  username: string;
  roles: string[];
};

export type AuthContextType = {
  token: string | null;
  user: AuthUser | null;
  isAuthenticated: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  logout: () => void;
};