package org.agro.agrohack.constants;

import lombok.Getter;

@Getter
public enum Substrate {
    GUMUS("Гумус"),KOKOS("Кокосовый"),TORPH("Торфяной"),
    POCHVOSMES("Почвосмесь"), PERLIT("Перлит");

    private String label;

    Substrate(String label) {
        this.label = label;
    }
}
