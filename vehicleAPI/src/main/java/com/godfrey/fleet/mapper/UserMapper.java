package com.godfrey.fleet.mapper;

import com.godfrey.fleet.dto.user.*;
import com.godfrey.fleet.model.Role;
import com.godfrey.fleet.model.User;
import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    // ---- DTO Mapping ----
    UserResponseDTO toResponse(User user);

    // When creating a new User, ignore DB-managed/service-handled fields
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // handled explicitly in UserService
    @Mapping(target = "active", ignore = true)   // defaulted by service/db
    User fromCreateDTO(UserCreateDTO dto);

    // When updating, ignore ID
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromDTO(UserUpdateDTO dto, @MappingTarget User user);

    // PATCH: ignore ID, password, active
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", ignore = true)
    void patchFromDTO(UserPatchDTO dto, @MappingTarget User user);

    // ----- Custom mapping methods for roles -----
    default Set<String> mapRolesToStrings(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    default Set<Role> mapStringsToRoles(Set<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream()
                .map(name -> {
                    Role role = new Role();
                    role.setName(name); // minimal stub; service layer can fetch full entity
                    return role;
                })
                .collect(Collectors.toSet());
    }
}