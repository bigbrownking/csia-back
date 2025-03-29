package org.agro.agrohack.model.indicators;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Indicator {
    private double humidity;
    private double temperatureAir;
    private double temperatureGround;
    private double height;
    private LocalDateTime date;
}
