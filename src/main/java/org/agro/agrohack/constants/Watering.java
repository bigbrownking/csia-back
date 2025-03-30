package org.agro.agrohack.constants;

import lombok.Getter;

@Getter
public enum Watering {
    Rukkola("Once every 2 days"),Watercress("Once every 1-2 weeks"), Mustard("Once a week");

    private String label;

    Watering(String label) {
        this.label = label;
    }
}
