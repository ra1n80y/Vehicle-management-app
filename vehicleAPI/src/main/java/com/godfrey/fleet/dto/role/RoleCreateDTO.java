package com.godfrey.fleet.dto.role;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public class RoleCreateDTO {

    @NotBlank
    private String name;

    private Set<String> permissions;

    public RoleCreateDTO() {}

    public RoleCreateDTO(String name, Set<String> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<String> getPermissions() { return permissions; }
    public void setPermissions(Set<String> permissions) { this.permissions = permissions; }
}