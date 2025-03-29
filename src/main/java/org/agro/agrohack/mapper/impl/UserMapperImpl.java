package org.agro.agrohack.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.mapper.UserMapper;
import org.agro.agrohack.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    @Override
    public GetProfileResponse toGetProfileUser(User user) {
        GetProfileResponse getProfileResponse = new GetProfileResponse();

        getProfileResponse.setEmail(user.getEmail());
        getProfileResponse.setProfileImage(user.getProfileImage());
        getProfileResponse.setFio(user.getFio());
        getProfileResponse.setPlants(user.getPlants());

        return getProfileResponse;
    }
}
