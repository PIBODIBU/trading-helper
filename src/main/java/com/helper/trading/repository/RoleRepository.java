package com.helper.trading.repository;

import com.helper.trading.model.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository("RoleRepository")
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findFirstById(Long id);

    Set<Role> getAllByRoleName(String roleName);
}
