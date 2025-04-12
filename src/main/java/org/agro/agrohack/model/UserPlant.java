package org.agro.agrohack.model;

import lombok.Data;
import org.agro.agrohack.constants.Substrate;
import org.agro.agrohack.model.indicators.Indicator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserPlant {
    private String custom_name;
    private String email;
    private String plantId;
    private LocalDateTime plantTime;
    private Substrate substrate;
    private LocalDateTime collectTime;
    private String currentState;
    private List<Notes> notes = new ArrayList<>();
    private List<Indicator> indicators = new ArrayList<>();
    private LocalDateTime lastWateringDate;
}
