package org.agro.agrohack.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.agro.agrohack.constants.Difficulty;

@Getter
@Setter
public class AddPlantRequest {
    private String name;
    private String type;
    private Difficulty difficulty;

    private String when_water;
    private String tip_water;
    private String difficulty_water;

    private String preferredLight_light;
    private String suitableWith_light;

    private String toxicTo_toxic;
    private String power_toxic;

    private String scale_humidity;
    private String ratio_humidity;

    private String name_problems;
    private String detail_problems;


    private String name_pests;
    private String detail_pests;

    private String liquid_fertilizing;

    private String sentence_temperature;
    private double start_temperature;
    private double end_temperature;
    private String indoorMonths_temperature;
    private String outDoorMonths_temperature;

    private String clean;

    private String color_leaves;
    private String type_leaves;

    private int beginHeight_dim;
    private int endHeight_dim;
    private int beginSpread_dim;
    private int endSpread_dim;

}
