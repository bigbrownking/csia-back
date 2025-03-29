package org.agro.agrohack.constants;

import lombok.Getter;

@Getter
public enum Difficulty {
    Easy("Easy"), Moderate("Moderate"), Advanced("Advanced");
    private String label;
    Difficulty(String label) {
        this.label = label;
    }
}
