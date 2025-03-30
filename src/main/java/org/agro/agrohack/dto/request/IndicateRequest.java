package org.agro.agrohack.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class IndicateRequest {
    private String custom_name;
    @Nullable
    private int humidity;
    @Nullable
    private int temperatureGround;
    @Nullable
    private int temperatureAir;
    @Nullable
    private int height;
}
