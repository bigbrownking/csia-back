package org.agro.agrohack.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SeedPlantRequest {
    private String email;
    private String plant_name;
    private LocalDateTime plantTime;
    private String substrate;
    private LocalDateTime collectionTime;
    private String notes;
}
