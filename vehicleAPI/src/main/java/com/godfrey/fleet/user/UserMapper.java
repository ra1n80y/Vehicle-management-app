package com.godfrey.fleet.user;

import com.godfrey.fleet.role.Role;
import com.godfrey.fleet.user.dto.UserCreateDTO;
import com.godfrey.fleet.user.dto.UserPatchDTO;
import com.godfrey.fleet.user.dto.UserResponseDTO;
import com.godfrey.fleet.user.dto.UserUpdateDTO;
import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    UserResponseDTO toResponse(User user);

    // CREATE: ignore id, password, roles (set explicitly in service)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User fromCreateDTO(UserCreateDTO dto);

    // UPDATE: ignore id, password, roles
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateFromDTO(UserUpdateDTO dto, @MappingTarget User user);

    // PATCH: ignore id, password, roles
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void patchFromDTO(UserPatchDTO dto, @MappingTarget User user);

    default Set<String> mapRolesToStrings(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}