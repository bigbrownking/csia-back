package org.agro.agrohack.model.plantCharacteristics;

import lombok.Data;

@Data
public class TemperatureAndSites {
    private String sentence;
    private double start;
    private double end;
    private String indoorMonths;
    private String outDoorMonths;
}
