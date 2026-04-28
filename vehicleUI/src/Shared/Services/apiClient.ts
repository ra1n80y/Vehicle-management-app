import { getToken } from "../../Features/Auth/Utils/token";
import { notifyUnauthorized } from "./authEvents";

const API_BASE_URL = "http://localhost:8080";

type RequestOptions = RequestInit & {
  auth?: boolean;
};

async function request<T>(
  endpoint: string,
  options: RequestOptions = {},
): Promise<T> {
  const { auth = true, headers, ...rest } = options;
  const token = getToken();
  const mergedHeaders = new Headers(headers);

  if (!(rest.body instanceof FormData)) {
    mergedHeaders.set("Content-Type", "application/json");
  }

  if (auth && token) {
    mergedHeaders.set("Authorization", `Bearer ${token}`);
  }

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...rest,
    headers: mergedHeaders,
  });

  if (response.status === 401) {
    notifyUnauthorized();
    throw new Error("Your session has expired. Please log in again.");
  }

  if (response.status === 403) {
    throw new Error("You do not have permission to perform this action.");
  }

  if (!response.ok) {
    let errorMessage = `HTTP error: ${response.status}`;
    try {
      const errorBody = await response.json();
      if (errorBody?.message) {
        errorMessage = errorBody.message;
      } else if (errorBody?.error) {
        errorMessage = errorBody.error;
      }
    } catch {
      // keep fallback error message
    }
    throw new Error(errorMessage);
  }

  if (response.status === 204) {
    return undefined as T;
  }

  return response.json() as Promise<T>;
}

export const apiClient = {
  get: <T>(endpoint: string, auth = true) =>
    request<T>(endpoint, { method: "GET", auth }),

  post: <TResponse, TBody>(endpoint: string, body: TBody, auth = true) =>
    request<TResponse>(endpoint, {
      method: "POST",
      auth,
      body: JSON.stringify(body),
    }),

  put: <TResponse, TBody>(endpoint: string, body: TBody, auth = true) =>
    request<TResponse>(endpoint, {
      method: "PUT",
      auth,
      body: JSON.stringify(body),
    }),

  patch: <TResponse, TBody>(endpoint: string, body: TBody, auth = true) =>
    request<TResponse>(endpoint, {
      method: "PATCH",
      auth,
      body: JSON.stringify(body),
    }),

  delete: <T>(endpoint: string, auth = true) =>
    request<T>(endpoint, { method: "DELETE", auth }),
};