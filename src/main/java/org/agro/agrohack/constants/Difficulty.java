package org.agro.agrohack.constants;

import lombok.Getter;

@Getter
public enum Difficulty {
    EASY("EASY"), MODERATE("MODERATE"), ADVANCED("ADVANCED");
    private String label;
    Difficulty(String label) {
        this.label = label;
    }
}
