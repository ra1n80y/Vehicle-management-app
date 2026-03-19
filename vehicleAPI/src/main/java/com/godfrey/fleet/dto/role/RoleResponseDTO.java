package com.godfrey.fleet.dto.role;

import java.util.Set;

public class RoleResponseDTO {

    private Long id;
    private String name;
    private Set<String> permissions;

    public RoleResponseDTO() {}

    public RoleResponseDTO(Long id, String name, Set<String> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<String> getPermissions() { return permissions; }
    public void setPermissions(Set<String> permissions) { this.permissions = permissions; }
}