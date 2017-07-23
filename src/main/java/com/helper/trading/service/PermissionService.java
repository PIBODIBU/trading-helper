package com.helper.trading.service;

import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.model.security.Permission;
import com.helper.trading.model.security.Role;
import com.helper.trading.model.user.User;
import com.helper.trading.repository.PermissionRepository;
import com.helper.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("PermissionService")
@Transactional(value = "txManager", readOnly = true)
public class PermissionService {
    private PermissionRepository permissionRepository;
    private UserRepository userRepository;
    private SecurityService securityService;

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_PERMISSIONS_READ')")
    public Permission get(Long id) {
        return permissionRepository.findFirstById(id);
    }

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_PERMISSIONS_READ')")
    public Set<Permission> get(String name) {
        return permissionRepository.getAllByName(name);
    }

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_PERMISSIONS_CREATE')")
    @Transactional
    public Permission create(Permission permission) {
        return permissionRepository.save(permission);
    }

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_PERMISSIONS_READ')")
    public Set<Permission> getMyPermissions() {
        Set<Permission> permissions = new HashSet<>();
        User user = userRepository.getFirstById(securityService.getUserFromContext().getId());

        for (Role role : user.getRoles()) {
            permissions.addAll(role.getPermissions());
        }

        return permissions;
    }

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_PERMISSIONS_READ')")
    public Set<Permission> getUserPermissions(Long userId) {
        Set<Permission> permissions = new HashSet<>();
        User user = userRepository.getFirstById(userId);

        for (Role role : user.getRoles()) {
            permissions.addAll(role.getPermissions());
        }

        return permissions;
    }

    @Autowired
    public void setPermissionRepository(@Qualifier("PermissionRepository")
                                                PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Autowired
    public void setUserRepository(@Qualifier("UserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSecurityService(@Qualifier("SecurityService") SecurityService securityService) {
        this.securityService = securityService;
    }
}
