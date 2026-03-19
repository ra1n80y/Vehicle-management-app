package com.godfrey.fleet.service.role;

import com.godfrey.fleet.dto.role.*;
import java.util.List;

public interface IRoleService {
    RoleResponseDTO createRole(RoleCreateDTO dto);
    RoleResponseDTO updateRole(Long id, RoleUpdateDTO dto);
    void deleteRole(Long id);
    RoleResponseDTO getRole(Long id);
    List<RoleResponseDTO> listRoles();
}