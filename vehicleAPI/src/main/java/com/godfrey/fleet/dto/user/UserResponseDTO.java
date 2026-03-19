package com.godfrey.fleet.dto.user;

import java.util.Set;

public class UserResponseDTO {

    private Long id;
    private String username;
    private Set<String> roles;
    private boolean active;

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String username, Set<String> roles, boolean active) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}