package org.agro.agrohack.repository;

import org.agro.agrohack.model.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantsRepository extends MongoRepository<Plant, String> {
    Page<Plant> getPlantByDifficulty(String difficulty, Pageable pageable);

}
