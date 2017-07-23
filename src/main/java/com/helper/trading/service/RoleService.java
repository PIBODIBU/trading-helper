package com.helper.trading.service;

import com.helper.trading.model.security.Role;
import com.helper.trading.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service("RoleService")
@Transactional(value = "txManager", readOnly = true)
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(@Qualifier("RoleRepository")
                                          RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_ROLE_READ')")
    public Role getRole(Long id) {
        return roleRepository.findFirstById(id);
    }

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_ROLE_READ')")
    public Set<Role> get(String roleName) {
        return roleRepository.getAllByRoleName(roleName);
    }

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_ROLE_CREATE')")
    @Transactional
    public Role create(Role role) {
        return roleRepository.save(role);
    }
}
