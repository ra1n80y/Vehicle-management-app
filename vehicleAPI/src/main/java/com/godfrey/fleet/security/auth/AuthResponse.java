package com.godfrey.fleet.security.auth;

import java.util.List;

public record AuthResponse(
        String token,
        String username,
        List<String> roles,
        List<String> permissions
) {}