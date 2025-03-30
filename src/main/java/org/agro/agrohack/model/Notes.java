package org.agro.agrohack.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notes {
    private String note;
    private LocalDateTime tillDate;
}
