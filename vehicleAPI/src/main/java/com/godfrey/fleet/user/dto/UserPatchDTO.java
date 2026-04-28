package com.godfrey.fleet.user.dto;

import java.util.Set;

public class UserPatchDTO {

    private String username;
    private String password;
    private Set<String> roles;
    private Boolean active;

    public UserPatchDTO() {}

    public UserPatchDTO(String username, String password, Set<String> roles, Boolean active) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.active = active;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}