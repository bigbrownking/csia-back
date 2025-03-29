package org.agro.agrohack.mapper;

import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.model.Plant;

public interface PlantMapper {
    Plant toPlant(AddPlantRequest addPlantRequest);
}
