package org.agro.agrohack.service;

import org.agro.agrohack.model.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlantService {
    Page<Plant> getAllPlants(Pageable pageable);
    Page<Plant> getPlantsByDifficulty(String difficulty, Pageable pageable);
}
