package com.godfrey.fleet.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Set<Permission> findByNameIn(Collection<String> names);

    Optional<Permission> findByName (String name);
}