package com.godfrey.fleet.service.user;

import com.godfrey.fleet.dto.user.*;
import java.util.List;

public interface IUserService {
    UserResponseDTO createUser(UserCreateDTO dto);
    UserResponseDTO updateUser(Long id, UserUpdateDTO dto);
    UserResponseDTO patchUser(Long id, UserPatchDTO dto);
    void deleteUser(Long id);
    UserResponseDTO getUser(Long id);
    List<UserResponseDTO> listUsers();
}