package org.agro.agrohack.service.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.repository.PlantsRepository;
import org.agro.agrohack.service.PlantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {
    private final PlantsRepository plantsRepository;

    @Override
    public Page<Plant> getAllPlants(Pageable pageable) {
        return plantsRepository.findAll(pageable);
    }

    @Override
    public Page<Plant> getPlantsByDifficulty(String difficulty, Pageable pageable) {
        return plantsRepository.getPlantByDifficulty(difficulty, pageable);
    }
}
