package com.godfrey.fleet.role;

import com.godfrey.fleet.role.dto.RoleCreateDTO;
import com.godfrey.fleet.role.dto.RolePatchDTO;
import com.godfrey.fleet.role.dto.RoleResponseDTO;
import com.godfrey.fleet.role.dto.RoleUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {

    @Mapping(target = "permissions", expression = "java(mapPermissionNames(role.getPermissions()))")
    RoleResponseDTO toResponse(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    Role fromCreateDTO(RoleCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateFromDTO(RoleUpdateDTO dto, @MappingTarget Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void patchFromDTO(RolePatchDTO dto, @MappingTarget Role role);

    default java.util.Set<String> mapPermissionNames(java.util.Set<Permission> permissions) {
        if (permissions == null) {
            return java.util.Collections.emptySet();
        }

        return permissions.stream()
                .map(Permission::getName)
                .collect(java.util.stream.Collectors.toSet());
    }
}