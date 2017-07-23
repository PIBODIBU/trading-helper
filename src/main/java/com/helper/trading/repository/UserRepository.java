package com.helper.trading.repository;

import com.helper.trading.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends CrudRepository<User, Long> {
    User getByUsername(String username);

    User getFirstById(Long id);
}
