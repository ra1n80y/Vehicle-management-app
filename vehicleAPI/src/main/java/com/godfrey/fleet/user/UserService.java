package com.godfrey.fleet.user;

import com.godfrey.fleet.audit.AuditAction;
import com.godfrey.fleet.audit.AuditLogService;
import com.godfrey.fleet.common.exception.DuplicateResourceException;
import com.godfrey.fleet.common.exception.ResourceNotFoundException;
import com.godfrey.fleet.role.Role;
import com.godfrey.fleet.role.RoleRepository;
import com.godfrey.fleet.user.dto.UserCreateDTO;
import com.godfrey.fleet.user.dto.UserPatchDTO;
import com.godfrey.fleet.user.dto.UserResponseDTO;
import com.godfrey.fleet.user.dto.UserUpdateDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuditLogService auditLogService;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       AuditLogService auditLogService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).USER_CREATE)")
    public UserResponseDTO createUser(UserCreateDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already taken");
        }

        User user = userMapper.fromCreateDTO(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(resolveRoles(dto.getRoles()));

        User saved = userRepository.save(user);
        auditLogService.log(AuditAction.CREATE, "User", saved.getId(), saved.getUsername());
        return userMapper.toResponse(saved);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).USER_UPDATE)")
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        User user = getUserEntity(id);

        if (!user.getUsername().equals(dto.getUsername()) &&
                userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already in use");
        }

        userMapper.updateFromDTO(dto, user);
        user.setRoles(resolveRoles(dto.getRoles()));

        User saved = userRepository.save(user);
        auditLogService.log(AuditAction.UPDATE, "User", saved.getId(), saved.getUsername());
        return userMapper.toResponse(saved);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).USER_UPDATE)")
    public UserResponseDTO patchUser(Long id, UserPatchDTO dto) {
        User user = getUserEntity(id);

        if (dto.getUsername() != null &&
                !dto.getUsername().equals(user.getUsername()) &&
                userRepository.existsByUsernameAndIdNot(dto.getUsername(), id)) {
            throw new DuplicateResourceException("Username already in use");
        }

        userMapper.patchFromDTO(dto, user);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setRoles(resolveRoles(dto.getRoles()));

        User saved = userRepository.save(user);
        auditLogService.log(AuditAction.PATCH, "User", saved.getId(), saved.getUsername());
        return userMapper.toResponse(saved);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).USER_DELETE)")
    public void deleteUser(Long id) {
        User user = getUserEntity(id);
        Long userId = user.getId();
        userRepository.delete(user);
        auditLogService.log(AuditAction.DELETE, "User", userId, user.getUsername());
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).USER_READ)")
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id) {
        User user = getUserEntity(id);
        return userMapper.toResponse(user);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).USER_READ)")
    @Transactional(readOnly = true)
    public List<UserResponseDTO> listUsers() {
        return userRepository.findAllWithRoles()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    // ---------- private helpers ----------

    private User getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    private Set<Role> resolveRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return Collections.emptySet();
        }
        return roleRepository.findByNameIn(roleNames);
    }
}