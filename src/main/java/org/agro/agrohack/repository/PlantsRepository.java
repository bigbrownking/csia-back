package org.agro.agrohack.repository;

import org.agro.agrohack.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantsRepository extends JpaRepository<Plant, Long> {
}
