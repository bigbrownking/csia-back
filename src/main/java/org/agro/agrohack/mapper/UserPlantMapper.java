package org.agro.agrohack.mapper;

import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.UserPlant;

public interface UserPlantMapper {
    UserPlant toUserPlant(AddPlantRequest addPlantRequest) throws NotFoundException;
}
