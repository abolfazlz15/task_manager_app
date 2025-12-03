package com.example.task_manager_app.auth.config;


import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("authManager")
public class AuthorizationManager {
    private static final String SUPERUSER = "SUPERUSER";

    public boolean hasPermission(Authentication authentication, String permission) {
        if (authentication == null) return false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null) return false;

        // TODO: SUERUSER checking is not implemented
        if (authorities.stream().anyMatch(a -> Objects.equals(a.getAuthority(), SUPERUSER))) {
            return true;
        }

        return authorities.stream().anyMatch(a -> Objects.equals(a.getAuthority(), permission));
    }

    public boolean hasAnyPermission(Authentication authentication, String[] permissions) {
        if (authentication == null) return false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null) return false;

        if (authorities.stream().anyMatch(a -> Objects.equals(a.getAuthority(), SUPERUSER))) {
            return true;
        }

        return Arrays.stream(permissions)
                .anyMatch(p -> authorities.stream().anyMatch(a -> Objects.equals(a.getAuthority(), p)));
    }
}
