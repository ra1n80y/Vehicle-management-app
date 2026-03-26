package com.godfrey.fleet.role;

import com.godfrey.fleet.role.dto.RoleCreateDTO;
import com.godfrey.fleet.role.dto.RolePatchDTO;
import com.godfrey.fleet.role.dto.RoleResponseDTO;
import com.godfrey.fleet.role.dto.RoleUpdateDTO;

import java.util.List;

public interface IRoleService {
    RoleResponseDTO createRole(RoleCreateDTO dto);
    RoleResponseDTO updateRole(Long id, RoleUpdateDTO dto);

    RoleResponseDTO patchRole (Long id, RolePatchDTO dto);

    void deleteRole(Long id);
    RoleResponseDTO getRole(Long id);
    List<RoleResponseDTO> listRoles();
}