package org.agro.agrohack.constants;

import lombok.Getter;

@Getter
public enum Difficulty {
    EASY("Easy"), MODERATE("Moderate"), ADVANCED("Advanced");
    private String label;
    Difficulty(String label) {
        this.label = label;
    }
}
