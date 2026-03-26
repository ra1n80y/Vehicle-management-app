package com.godfrey.fleet.user.dto;

import java.util.Set;

public class UserPatchDTO {

    private String username; // optional
    private String password; // optional
    private Set<String> roles; // optional

    public UserPatchDTO() {}

    public UserPatchDTO(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}