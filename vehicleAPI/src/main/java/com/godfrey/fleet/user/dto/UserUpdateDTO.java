package com.godfrey.fleet.user.dto;

import java.util.Set;

public class UserUpdateDTO {

    private String username;
    private Set<String> roles;
    private Boolean active;   // ADDED

    public UserUpdateDTO() {}

    public UserUpdateDTO(String username, Set<String> roles, Boolean active) {
        this.username = username;
        this.roles = roles;
        this.active = active;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}