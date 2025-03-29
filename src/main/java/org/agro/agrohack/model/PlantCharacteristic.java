package org.agro.agrohack.model;

import lombok.Data;
import org.agro.agrohack.model.plantCharacteristics.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class PlantCharacteristic {
    @Field("water")
    private Water water;
    private Light light;
    private Toxicity toxicity;
    private Humidity humidity;
    private CommonProblems common_problems;
    private CommonPests common_pests;
    private String liquid_fertilizing;
    private TemperatureAndSites temperatures;
    private String clean;
    private Leaves leaves;
    private Dimensions dimensions;
}
