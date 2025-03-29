package org.agro.agrohack.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.constants.Substrate;
import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.mapper.UserPlantMapper;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.repository.PlantsRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPlantMapperImpl implements UserPlantMapper {
    private final PlantsRepository plantsRepository;

    @Override
    public UserPlant toUserPlant(AddPlantRequest addPlantRequest) throws NotFoundException {
        Plant plant = plantsRepository.getPlantByName(addPlantRequest.getPlant_name()).orElseThrow(()->new NotFoundException("Plant not found..."));
        UserPlant userPlant = new UserPlant();

        userPlant.setEmail(addPlantRequest.getEmail());
        userPlant.setPlantTime(addPlantRequest.getPlantTime());
        userPlant.setPlantId(plant.getId());
        userPlant.setSubstrate(Substrate.valueOf(addPlantRequest.getSubstrate()));
        userPlant.setCollectTime(addPlantRequest.getCollectionTime());

        return userPlant;
    }
}
