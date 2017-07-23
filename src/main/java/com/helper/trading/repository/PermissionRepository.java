package com.helper.trading.repository;

import com.helper.trading.model.security.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository("PermissionRepository")
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    Permission findFirstById(Long id);

    Set<Permission> getAllByName(String name);
}
