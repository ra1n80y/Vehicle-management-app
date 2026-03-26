package com.godfrey.fleet.common.config;

import com.godfrey.fleet.role.Permission;
import com.godfrey.fleet.role.Role;
import com.godfrey.fleet.user.User;
import com.godfrey.fleet.role.PermissionRepository;
import com.godfrey.fleet.role.RoleRepository;
import com.godfrey.fleet.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.godfrey.fleet.security.Permissions.VEHICLE_CREATE;
import static com.godfrey.fleet.security.Permissions.VEHICLE_DELETE;
import static com.godfrey.fleet.security.Permissions.VEHICLE_READ;
import static com.godfrey.fleet.security.Permissions.VEHICLE_UPDATE;

@Configuration
public class RbacSeeder implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RbacSeeder(
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {

        Permission read = createPermission(VEHICLE_READ, "Allows reading vehicle data");
        Permission create = createPermission(VEHICLE_CREATE, "Allows creating vehicles");
        Permission update = createPermission(VEHICLE_UPDATE, "Allows updating vehicles");
        Permission delete = createPermission(VEHICLE_DELETE, "Allows deleting vehicles");

        Role superAdmin = createOrUpdateRole("SUPERADMIN", mutableSet(read, create, update, delete));
        Role admin = createOrUpdateRole("ADMIN", mutableSet(read, create, update, delete));
        Role manager = createOrUpdateRole("MANAGER", mutableSet(read, create, update));
        Role clerk = createOrUpdateRole("CLERK", mutableSet(read, create));
        Role auditor = createOrUpdateRole("AUDITOR", mutableSet(read));

        upsertUser("superadmin", "super123", true, mutableSet(superAdmin));
        upsertUser("admin.ops", "admin123", true, mutableSet(admin));
        upsertUser("fleet.manager", "manager123", true, mutableSet(manager));
        upsertUser("vehicle.clerk", "clerk123", true, mutableSet(clerk));
        upsertUser("auditor.readonly", "audit123", true, mutableSet(auditor));
    }

    private Permission createPermission(String name, String description) {
        return permissionRepository.findByName(name)
                .map(existing -> {
                    existing.setDescription(description);
                    return permissionRepository.save(existing);
                })
                .orElseGet(() -> permissionRepository.save(new Permission(name, description)));
    }

    private Role createOrUpdateRole(String name, Set<Permission> permissions) {
        return roleRepository.findByName(name)
                .map(existing -> {
                    existing.setPermissions(new HashSet<>(permissions));
                    return roleRepository.save(existing);
                })
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    role.setPermissions(new HashSet<>(permissions));
                    return roleRepository.save(role);
                });
    }

    private void upsertUser(
            String username,
            String rawPassword,
            boolean active,
            Set<Role> roles
    ) {
        User user = userRepository.findByUsername(username).orElseGet(User::new);

        user.setUsername(username);

        if (user.getPassword() == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        user.setActive(active);
        user.setRoles(new HashSet<>(roles));

        userRepository.save(user);
    }

    @SafeVarargs
    private final <T> Set<T> mutableSet(T... items) {
        return new HashSet<>(Set.of(items));
    }
}