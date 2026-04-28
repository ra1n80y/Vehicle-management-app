import {
  createContext,
  useCallback,
  useEffect,
  useMemo,
  useState,
  type ReactNode,
} from "react";
import { AuthService } from "../Services/AuthService";
import { getUserFromToken, isTokenExpired } from "../Utils/jwt";
import { getToken, removeToken, setToken } from "../Utils/token";
import type { AuthContextType, AuthUser, LoginRequest } from "../Types/Auth";
import {
  clearUnauthorizedListener,
  registerUnauthorizedListener,
} from "../../../Shared/Services/authEvents";

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined,
);

type AuthProviderProps = {
  children: ReactNode;
};

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [token, setTokenState] = useState<string | null>(null);
  const [user, setUser] = useState<AuthUser | null>(null);
  const [isInitializing, setIsInitializing] = useState(true);

  const logout = useCallback((): void => {
    removeToken();
    setTokenState(null);
    setUser(null);
  }, []);

  useEffect(() => {
    const storedToken = getToken();

    if (!storedToken) {
      setIsInitializing(false);
      return;
    }

    if (isTokenExpired(storedToken)) {
      logout();
      setIsInitializing(false);
      return;
    }

    const parsedUser = getUserFromToken(storedToken);

    if (!parsedUser) {
      logout();
      setIsInitializing(false);
      return;
    }

    setTokenState(storedToken);
    setUser(parsedUser);
    setIsInitializing(false);
  }, [logout]);

  useEffect(() => {
    registerUnauthorizedListener(() => {
      logout();
    });

    return () => {
      clearUnauthorizedListener();
    };
  }, [logout]);

  const login = async (credentials: LoginRequest): Promise<void> => {
    const response = await AuthService.login(credentials);

    const parsedUser = getUserFromToken(response.token);

    if (!parsedUser) {
      throw new Error("Invalid authentication token received.");
    }

    setToken(response.token);
    setTokenState(response.token);
    setUser(parsedUser);
  };

  const value = useMemo<AuthContextType>(
    () => ({
      token,
      user,
      isAuthenticated: !!token && !!user,
      isInitializing,
      login,
      logout,
    }),
    [token, user, isInitializing, logout],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
