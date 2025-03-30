package org.agro.agrohack.constants;

import lombok.Getter;

@Getter
public enum Watering {
    RUKKOLA("Once every 2 days"),WATERCRESS("Once every 1-2 weeks"), MUSTARD("Once a week");

    private String label;

    Watering(String label) {
        this.label = label;
    }
}
