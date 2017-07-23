package com.helper.trading.application.configuration.security;

import com.helper.trading.model.security.Permission;
import com.helper.trading.model.security.Role;
import com.helper.trading.model.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("SecurityService")
public class SecurityService {
    public boolean hasPermission(String permission) {
        UserDetails userDetails = ((UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        User user = userDetails.getUser();

        for (Role role : user.getRoles()) {
            for (Permission rolePermission : role.getPermissions()) {
                if (rolePermission.getName().equals(permission))
                    return true;
            }
        }

        return false;
    }

    public User getUserFromContext() {
        return ((UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }
}

