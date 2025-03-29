package org.agro.agrohack.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class IndicateRequest {
    private String custom_name;
    @Nullable
    private double humidity;
    @Nullable
    private double temperatureGround;
    @Nullable
    private double temperatureAir;
    @Nullable
    private double height;
}
