package org.agro.agrohack.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.constants.Substrate;
import org.agro.agrohack.dto.request.SeedPlantRequest;
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
    public UserPlant toUserPlant(SeedPlantRequest seedPlantRequest) throws NotFoundException {
        Plant plant = plantsRepository.getPlantByName(seedPlantRequest.getPlant_name()).orElseThrow(()->new NotFoundException("Plant not found..."));
        UserPlant userPlant = new UserPlant();

        userPlant.setCustom_name(seedPlantRequest.getCustom_name());
        userPlant.setEmail(seedPlantRequest.getEmail());
        userPlant.setPlantTime(seedPlantRequest.getPlantTime());
        userPlant.setPlantId(plant.getId());
        userPlant.setSubstrate(Substrate.fromLabel(seedPlantRequest.getSubstrate()));
        userPlant.setCollectTime(seedPlantRequest.getCollectionTime());
        userPlant.setNotes(seedPlantRequest.getNotes());
        userPlant.setLastWateringDate(null);

        return userPlant;
    }
}
