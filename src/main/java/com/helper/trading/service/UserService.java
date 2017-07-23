package com.helper.trading.service;

import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.model.user.User;
import com.helper.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
@Transactional(value = "txManager", readOnly = true)
public class UserService {
    private UserRepository userRepository;
    private SecurityService securityService;

    @PreAuthorize("@SecurityService.hasPermission('PERM_PROFILE_USER_READ')")
    public User getUser(Long id) {
        return userRepository.getFirstById(id);
    }

    public User getMyData() {
        return userRepository.getFirstById(securityService.getUserFromContext().getId());
    }

    @PreAuthorize("@SecurityService.hasPermission('PERM_USER_CREATE')")
    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Autowired
    public void setUserRepository(@Qualifier("UserRepository")
                                          UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSecurityService(@Qualifier("SecurityService") SecurityService securityService) {
        this.securityService = securityService;
    }
}