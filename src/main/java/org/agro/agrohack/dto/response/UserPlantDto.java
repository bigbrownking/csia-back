package org.agro.agrohack.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserPlantDto {

    private LocalDateTime plantTime;
    private LocalDateTime collectTime;
    private String substrate;
    private String currentState;
}
