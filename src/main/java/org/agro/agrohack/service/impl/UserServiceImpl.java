package org.agro.agrohack.service.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.Role;
import org.agro.agrohack.model.User;
import org.agro.agrohack.repository.RoleRepository;
import org.agro.agrohack.repository.UserRepository;
import org.agro.agrohack.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public Page<User> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found..."));
    }
    public void saveUser(User user) throws NotFoundException {
        Role defaultRole = roleRepository.getRoleByName("user").orElseThrow(()-> new NotFoundException("Default role not found..."));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(defaultRole);

        userRepository.save(user);
    }
}
