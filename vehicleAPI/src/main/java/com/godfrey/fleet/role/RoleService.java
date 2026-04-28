package com.godfrey.fleet.role;

import com.godfrey.fleet.audit.AuditAction;
import com.godfrey.fleet.audit.AuditLogService;
import com.godfrey.fleet.common.exception.BadRequestException;
import com.godfrey.fleet.common.exception.DuplicateResourceException;
import com.godfrey.fleet.common.exception.ResourceNotFoundException;
import com.godfrey.fleet.role.dto.RoleCreateDTO;
import com.godfrey.fleet.role.dto.RolePatchDTO;
import com.godfrey.fleet.role.dto.RoleResponseDTO;
import com.godfrey.fleet.role.dto.RoleUpdateDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final AuditLogService auditLogService;

    public RoleService(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            RoleMapper roleMapper,
            AuditLogService auditLogService
    ) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
        this.auditLogService = auditLogService;
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).ROLE_MANAGE)")
    public RoleResponseDTO createRole(RoleCreateDTO dto) {
        checkDuplicateName(dto.getName(), null);

        Role role = roleMapper.fromCreateDTO(dto);
        role.setPermissions(resolvePermissions(dto.getPermissions()));

        Role saved = roleRepository.save(role);
        auditLogService.log(AuditAction.CREATE, "Role", saved.getId(), "system");
        return roleMapper.toResponse(saved);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).ROLE_MANAGE)")
    public RoleResponseDTO updateRole(Long id, RoleUpdateDTO dto) {
        Role role = findRoleByIdOrThrow(id);
        checkDuplicateName(dto.getName(), id);

        roleMapper.updateFromDTO(dto, role);
        if (dto.getPermissions() != null) {
            role.setPermissions(resolvePermissions(dto.getPermissions()));
        }

        Role saved = roleRepository.save(role);
        auditLogService.log(AuditAction.UPDATE, "Role", saved.getId(), "system");
        return roleMapper.toResponse(saved);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).ROLE_MANAGE)")
    public RoleResponseDTO patchRole(Long id, RolePatchDTO dto) {
        Role role = findRoleByIdOrThrow(id);
        if (dto.getName() != null) {
            checkDuplicateName(dto.getName(), id);
        }

        roleMapper.patchFromDTO(dto, role);
        if (dto.getPermissions() != null) {
            role.setPermissions(resolvePermissions(dto.getPermissions()));
        }

        Role saved = roleRepository.save(role);
        auditLogService.log(AuditAction.PATCH, "Role", saved.getId(), "system");
        return roleMapper.toResponse(saved);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).ROLE_MANAGE)")
    public void deleteRole(Long id) {
        Role role = findRoleByIdOrThrow(id);
        roleRepository.delete(role);
        auditLogService.log(AuditAction.DELETE, "Role", role.getId(), "system");
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).ROLE_READ)")
    @Transactional(readOnly = true)
    public RoleResponseDTO getRole(Long id) {
        Role role = findRoleByIdOrThrow(id);
        return roleMapper.toResponse(role);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).ROLE_READ)")
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> listRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    private Role findRoleByIdOrThrow(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));
    }

    private void checkDuplicateName(String name, Long excludeId) {
        roleRepository.findByName(name).ifPresent(existing -> {
            if (excludeId == null || !existing.getId().equals(excludeId)) {
                throw new DuplicateResourceException("Role already exists: " + name);
            }
        });
    }

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
            throw new BadRequestException("Invalid permissions: " + missing);
        }
        return permissions;
    }
}