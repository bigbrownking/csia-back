package org.agro.agrohack.repository;

import org.agro.agrohack.model.Plant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantsRepository extends MongoRepository<Plant, String> {
}
