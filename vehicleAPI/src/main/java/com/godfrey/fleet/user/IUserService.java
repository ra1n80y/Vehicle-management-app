package com.godfrey.fleet.user;

import com.godfrey.fleet.user.dto.UserCreateDTO;
import com.godfrey.fleet.user.dto.UserPatchDTO;
import com.godfrey.fleet.user.dto.UserResponseDTO;
import com.godfrey.fleet.user.dto.UserUpdateDTO;

import java.util.List;

public interface IUserService {
    UserResponseDTO createUser(UserCreateDTO dto);
    UserResponseDTO updateUser(Long id, UserUpdateDTO dto);
    UserResponseDTO patchUser(Long id, UserPatchDTO dto);
    void deleteUser(Long id);
    UserResponseDTO getUser(Long id);
    List<UserResponseDTO> listUsers();
}