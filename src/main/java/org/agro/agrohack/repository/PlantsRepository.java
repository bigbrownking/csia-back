package org.agro.agrohack.repository;

import org.agro.agrohack.model.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantsRepository extends MongoRepository<Plant, String> {
    Page<Plant> getPlantByDifficulty(String difficulty, Pageable pageable);
    Optional<Plant> getPlantByName(String name);
    Optional<Plant> getPlantById(String id);

    @Aggregation(pipeline = {
            "{ $addFields: { difficultyOrder: { $switch: { branches: [" +
                    "{ case: { $eq: [\"$difficulty\", \"Easy\"] }, then: 0 }," +
                    "{ case: { $eq: [\"$difficulty\", \"Moderate\"] }, then: 1 }," +
                    "{ case: { $eq: [\"$difficulty\", \"Advanced\"] }, then: 2 }" +
                    "], default: 3 } } } }",
            "{ $sort: { difficultyOrder: 1 } }"
    })
    List<Plant> getPlantsSortedByDifficulty();

}
