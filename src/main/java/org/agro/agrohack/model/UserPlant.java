package org.agro.agrohack.model;

import lombok.Data;
import org.agro.agrohack.constants.Substrate;

import java.time.LocalDateTime;

@Data
public class UserPlant {
    private String email;
    private String plantId;
    private LocalDateTime plantTime;
    private Substrate substrate;
    private LocalDateTime collectTime;
}
