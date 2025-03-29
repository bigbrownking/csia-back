package org.agro.agrohack.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddPlantRequest {
    private String type;
    private LocalDateTime plantTime;
    private String substrate;
    private LocalDateTime collectionTime;
}
