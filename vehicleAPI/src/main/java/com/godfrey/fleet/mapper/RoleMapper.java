package com.godfrey.fleet.mapper;

import com.godfrey.fleet.dto.role.*;
import com.godfrey.fleet.model.Role;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {

    // Convert Role entity to DTO
    RoleResponseDTO toResponse(Role role);

    // Create new Role: ignore ID (DB generated)
    @Mapping(target = "id", ignore = true)
    Role fromCreateDTO(RoleCreateDTO dto);

    // Update Role: ignore ID
    @Mapping(target = "id", ignore = true)
    void updateFromDTO(RoleUpdateDTO dto, @MappingTarget Role role);

    // PATCH Role: ignore ID
    @Mapping(target = "id", ignore = true)
    void patchFromDTO(RolePatchDTO dto, @MappingTarget Role role);
}