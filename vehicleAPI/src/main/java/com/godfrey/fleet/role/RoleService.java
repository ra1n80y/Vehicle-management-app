package com.godfrey.fleet.role;

import com.godfrey.fleet.common.exception.ResourceNotFoundException;
import com.godfrey.fleet.role.dto.RoleCreateDTO;
import com.godfrey.fleet.role.dto.RolePatchDTO;
import com.godfrey.fleet.role.dto.RoleResponseDTO;
import com.godfrey.fleet.role.dto.RoleUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleService(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            RoleMapper roleMapper
    ) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponseDTO createRole(RoleCreateDTO dto) {
        Role role = roleMapper.fromCreateDTO(dto);

        role.setPermissions(resolvePermissions(dto.getPermissions()));

        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponseDTO updateRole(Long id, RoleUpdateDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));

        roleMapper.updateFromDTO(dto, role);

        if (dto.getPermissions() != null) {
            role.setPermissions(resolvePermissions(dto.getPermissions()));
        }

        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponseDTO patchRole (Long id, RolePatchDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));

        roleMapper.patchFromDTO(dto, role);

        if (dto.getPermissions() != null) {
            role.setPermissions(resolvePermissions(dto.getPermissions()));
        }

        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));

        roleRepository.delete(role);
    }

    @Override
    public RoleResponseDTO getRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));

        return roleMapper.toResponse(role);
    }

    @Override
    public List<RoleResponseDTO> listRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🔥 THE CORE RBAC PIECE
    private Set<Permission> resolvePermissions(Set<String> permissionNames) {
        if (permissionNames == null || permissionNames.isEmpty()) {
            return new HashSet<>();
        }

        Set<Permission> permissions = permissionRepository.findByNameIn(permissionNames);

        if (permissions.size() != permissionNames.size()) {
            Set<String> found = permissions.stream()
                    .map(Permission::getName)
                    .collect(Collectors.toSet());

            Set<String> missing = permissionNames.stream()
                    .filter(name -> !found.contains(name))
                    .collect(Collectors.toSet());

            throw new IllegalArgumentException("Invalid permissions: " + missing);
        }

        return permissions;
    }
}