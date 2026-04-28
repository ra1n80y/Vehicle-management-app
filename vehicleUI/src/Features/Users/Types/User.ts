export interface UserResponseDTO {
  id: number;
  username: string;
  roles: string[];          // Role names
  active: boolean;
}

export interface UserCreateDTO {
  username: string;
  password: string;
  active?: boolean;         // optional, defaults to true on backend
  roles?: string[];         // optional, role names
}

export interface UserUpdateDTO {
  username?: string;
  active?: boolean;
  roles?: string[];
}

export interface UserPatchDTO {
  username?: string;
  password?: string;
  active?: boolean;
  roles?: string[];
}