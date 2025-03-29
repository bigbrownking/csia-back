package org.agro.agrohack.service;

import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(User user) throws NotFoundException;
    Page<User> getAllUsers(Pageable pageable);
}
