package com.godfrey.fleet.service.role;

import com.godfrey.fleet.dto.role.*;
import com.godfrey.fleet.exception.ResourceNotFoundException;
import com.godfrey.fleet.mapper.RoleMapper;
import com.godfrey.fleet.model.Role;
import com.godfrey.fleet.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService (RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponseDTO createRole(RoleCreateDTO dto) {
        Role role = roleMapper.fromCreateDTO(dto);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponseDTO updateRole(Long id, RoleUpdateDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));
        roleMapper.updateFromDTO(dto, role);
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
}