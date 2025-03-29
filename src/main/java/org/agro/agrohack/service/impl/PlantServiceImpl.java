package org.agro.agrohack.service.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.repository.PlantsRepository;
import org.agro.agrohack.service.PlantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {
    private final PlantsRepository plantsRepository;

    @Override
    public Page<Plant> getAllPlants(int page, int size) {
        List<Plant> plants = plantsRepository.getPlantsSortedByDifficulty();

        int start = Math.min(page * size, plants.size());
        int end = Math.min(start + size, plants.size());
        List<Plant> subList = plants.subList(start, end);

        return new PageImpl<>(subList, PageRequest.of(page, size), plants.size());
    }

    @Override
    public Page<Plant> getPlantsByDifficulty(String difficulty, Pageable pageable) {
        return plantsRepository.getPlantByDifficulty(difficulty, pageable);
    }
}
