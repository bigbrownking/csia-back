package org.agro.agrohack.mapper;

import org.agro.agrohack.dto.request.SeedPlantRequest;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.UserPlant;

public interface UserPlantMapper {
    UserPlant toUserPlant(SeedPlantRequest seedPlantRequest) throws NotFoundException;
}
