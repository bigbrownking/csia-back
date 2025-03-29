package org.agro.agrohack.service;

import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.exception.LowLevelException;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.User;
import org.agro.agrohack.model.UserPlant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(User user) throws NotFoundException;
    Page<User> getAllUsers(Pageable pageable);
    String promote(String email) throws NotFoundException;
    Page<UserPlant> myPlants(String email, int page, int size)throws NotFoundException;
    String createUserPlant(AddPlantRequest addPlantRequest) throws NotFoundException, LowLevelException;
    GetProfileResponse getProfile(String email) throws NotFoundException;
}
