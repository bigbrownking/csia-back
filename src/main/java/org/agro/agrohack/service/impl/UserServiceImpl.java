package org.agro.agrohack.service.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.dto.request.IndicateRequest;
import org.agro.agrohack.dto.request.SeedPlantRequest;
import org.agro.agrohack.dto.request.EditProfileRequest;
import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.exception.LowLevelException;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.mapper.IndicateMapper;
import org.agro.agrohack.mapper.PlantMapper;
import org.agro.agrohack.mapper.UserMapper;
import org.agro.agrohack.mapper.UserPlantMapper;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.model.Role;
import org.agro.agrohack.model.User;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.model.indicators.Indicator;
import org.agro.agrohack.repository.PlantsRepository;
import org.agro.agrohack.repository.RoleRepository;
import org.agro.agrohack.repository.UserRepository;
import org.agro.agrohack.service.UserService;
import org.agro.agrohack.utils.LevelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PlantsRepository plantsRepository;

    private final UserPlantMapper userPlantMapper;
    private final PlantMapper plantMapper;
    private final IndicateMapper indicateMapper;
    private final UserMapper userMapper;
    private final LevelService levelService;
    private final String DEFAULT_ROLE = "user";
    private final String HR_ROLE = "HR_ROLE";

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found..."));
    }

    public void saveUser(User user) throws NotFoundException {
        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE).orElseThrow(() -> new NotFoundException("Default role not found..."));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(defaultRole);


        userRepository.save(user);
    }

    @Override
    public String promote(String email) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found..."));
        Role role = roleRepository.findByName(user.getRole().getName()).orElseThrow(() -> new NotFoundException("Role not found..."));

        if (role.getName().equals(DEFAULT_ROLE)) {
            Role promotedRole = roleRepository.findByName(HR_ROLE).orElseThrow(() -> new NotFoundException("Role not found..."));
            user.setRole(promotedRole);

            userRepository.save(user);
            return "User updated to HR!";
        } else {
            return "You can't promote this user...";
        }
    }

    @Override
    public Page<UserPlant> myPlants(String email, int page, int size) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found..."));
        List<UserPlant> userPlants = user.getPlants();

        if (userPlants == null || userPlants.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
        }

        int start = Math.min(page * size, userPlants.size());
        int end = Math.min(start + size, userPlants.size());
        List<UserPlant> subList = userPlants.subList(start, end);

        return new PageImpl<>(subList, PageRequest.of(page, size), userPlants.size());
    }

    @Override
    public String createUserPlant(SeedPlantRequest seedPlantRequest) throws NotFoundException, LowLevelException {
        User user = userRepository.getUserByEmail(seedPlantRequest.getEmail()).orElseThrow(() -> new NotFoundException("User not found..."));

        Plant plant = plantsRepository.getPlantByName(seedPlantRequest.getPlant_name()).orElseThrow(() -> new NotFoundException("Plant not found..."));

        if (!levelService.isEnoughForPlant(user.getEmail(), plant.getName())) {
            throw new LowLevelException("Your level is not enough for this plant...");
        }

        user.getPlants().add(userPlantMapper.toUserPlant(seedPlantRequest));
        userRepository.save(user);
        return "Plant created!";
    }

    @Override
    public GetProfileResponse getProfile(String email) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found..."));

        return userMapper.toGetProfileUser(user);
    }

    @Override
    public String editProfile(String email, EditProfileRequest editProfileRequest) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(()-> new NotFoundException("User not found..."));
        boolean changed = false;

        if (editProfileRequest.getNewPassword() != null &&
                !passwordEncoder.matches(editProfileRequest.getNewPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(editProfileRequest.getNewPassword()));
            changed = true;
        }
        if (editProfileRequest.getFio() != null && !editProfileRequest.getFio().equals(user.getFio())) {
            user.setFio(editProfileRequest.getFio());
            changed = true;
        }
        if (changed) {
            userRepository.save(user);
            return "Profile updated!";
        } else {
            return "No changes were made to the profile.";
        }
    }

    @Override
    public void uploadProfileImage(String email, String url) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found..."));
        user.setProfileImage(url);

        userRepository.save(user);
    }

    @Override
    public String addPlantToVocabulary(AddPlantRequest addPlantRequest) {
        Plant plant = plantMapper.toPlant(addPlantRequest);
        plantsRepository.save(plant);
        return plant.getName() + "was added!";
    }

    @Override
    public String indicate(String email, IndicateRequest indicateRequest) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found..."));

        List<UserPlant> userPlants = user.getPlants();
        for(UserPlant userPlant : userPlants){
            if(!userPlant.getCustom_name().equals(indicateRequest.getCustom_name())) continue;

            Indicator indicator = indicateMapper.toIndicate(indicateRequest);
            userPlant.getIndicators().add(indicator);

            userRepository.save(user);
            return "Indication added!";
        }
        return "Plant not found...";
    }

    @Override
    public Page<Indicator> getIndicatorsOfUserPlant(String email, String customName, int page, int size) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found..."));

        List<UserPlant> userPlants = user.getPlants();

        for(UserPlant userPlant : userPlants){
            if(!userPlant.getCustom_name().equals(customName)) continue;

            List<Indicator> indicators = userPlant.getIndicators();
            int start = Math.min(page * size, indicators.size());
            int end = Math.min(start + size, indicators.size());
            List<Indicator> subList = indicators.subList(start, end);

            return new PageImpl<>(subList, PageRequest.of(page, size), indicators.size());
        }

        return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
    }

    @Override
    public List<UserPlant> allUsersPlants() {
        List<User> users = userRepository.findAll();
        List<UserPlant> allPlants = new ArrayList<>();

        for (User user : users) {
            allPlants.addAll(user.getPlants());
        }

        return allPlants;
    }

    @Override
    @Transactional
    public String deleteUser(String id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found..."));

        userRepository.delete(user);

        return "User deleted successfully";
    }


    @Override
    public String water(String email, String customName) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found..."));

        List<UserPlant> userPlants = user.getPlants();
        for(UserPlant userPlant : userPlants){
            if(!userPlant.getCustom_name().equals(customName)) continue;

            userPlant.setLastWateringDate(LocalDateTime.now());
            levelService.addExp(email, 10);
            userRepository.save(user);

            return "Watering...";
        }
        return "Plant not found...";
    }
}
