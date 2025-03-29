package org.agro.agrohack.service.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.mapper.UserMapper;
import org.agro.agrohack.mapper.UserPlantMapper;
import org.agro.agrohack.model.Role;
import org.agro.agrohack.model.User;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.repository.RoleRepository;
import org.agro.agrohack.repository.UserRepository;
import org.agro.agrohack.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final UserPlantMapper userPlantMapper;
    private final UserMapper userMapper;
    private final String DEFAULT_ROLE = "user";
    private final String HR_ROLE = "HR_ROLE";
    @Override
    public Page<User> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found..."));
    }
    public void saveUser(User user) throws NotFoundException {
        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE).orElseThrow(()-> new NotFoundException("Default role not found..."));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(defaultRole);

        userRepository.save(user);
    }

    @Override
    public String promote(String email) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(()->new NotFoundException("User not found..."));
        Role role = roleRepository.findByName(user.getRole().getName()).orElseThrow(()->new NotFoundException("Role not found..."));

        if(role.getName().equals(DEFAULT_ROLE)){
            Role promotedRole = roleRepository.findByName(HR_ROLE).orElseThrow(()->new NotFoundException("Role not found..."));
            user.setRole(promotedRole);

            userRepository.save(user);
            return "User updated to HR!";
        }else{
            return "You can't promote this user...";
        }
    }

    @Override
    public Page<UserPlant> myPlants(String email, int page, int size) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(()->new NotFoundException("User not found..."));
        List<UserPlant> userPlants = user.getPlants();

        int start = Math.min(page * size, userPlants.size());
        int end = Math.min(start + size, userPlants.size());
        List<UserPlant> subList = userPlants.subList(start, end);

        return new PageImpl<>(subList, PageRequest.of(page, size), userPlants.size());
    }

    @Override
    public String createUserPlant(AddPlantRequest addPlantRequest) throws NotFoundException {
        User user = userRepository.getUserByEmail(addPlantRequest.getEmail()).orElseThrow(()->new NotFoundException("User not found..."));
        user.getPlants().add(userPlantMapper.toUserPlant(addPlantRequest));

        return "Plant created!";
    }

    @Override
    public GetProfileResponse getProfile(String email) throws NotFoundException {
        User user =  userRepository.getUserByEmail(email).orElseThrow(()->new NotFoundException("User not found..."));

        return userMapper.toGetProfileUser(user);
    }
}
