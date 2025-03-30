package org.agro.agrohack.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
public class SeedPlantRequest {
    private String custom_name;
    private String email;
    private String plant_name;
    private LocalDateTime plantTime;
    private String substrate;
    private LocalDateTime collectionTime;
    private String notes;
    private LocalDateTime noteDate;
}
