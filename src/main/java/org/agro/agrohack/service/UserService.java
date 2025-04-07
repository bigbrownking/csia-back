package org.agro.agrohack.service;

import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.dto.request.IndicateRequest;
import org.agro.agrohack.dto.request.SeedPlantRequest;
import org.agro.agrohack.dto.request.EditProfileRequest;
import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.exception.LowLevelException;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.User;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.model.indicators.Indicator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user) throws NotFoundException;
    Page<User> getAllUsers(Pageable pageable);
    String promote(String email) throws NotFoundException;
    Page<UserPlant> myPlants(String email, int page, int size)throws NotFoundException;
    String createUserPlant(SeedPlantRequest seedPlantRequest) throws NotFoundException, LowLevelException;
    GetProfileResponse getProfile(String email) throws NotFoundException;
    String editProfile(String email, EditProfileRequest editProfileRequest) throws NotFoundException;
    void uploadProfileImage(String email, String url) throws NotFoundException;
    String addPlantToVocabulary(AddPlantRequest addPlantRequest);
    Page<Indicator> getIndicatorsOfUserPlant(String email, String customName, int page, int size) throws NotFoundException;
    String indicate(String email, IndicateRequest indicateRequest) throws NotFoundException;
    String water(String email, String customName) throws NotFoundException;
    String deleteUser(String id) throws NotFoundException;
    List<UserPlant> allUsersPlants();
}
