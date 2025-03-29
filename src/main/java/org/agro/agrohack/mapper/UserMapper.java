package org.agro.agrohack.mapper;

import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.model.User;

public interface UserMapper {
    GetProfileResponse toGetProfileUser(User user);
}
