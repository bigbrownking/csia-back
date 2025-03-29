package org.agro.agrohack.model;

import lombok.Data;
import org.agro.agrohack.constants.Difficulty;
import org.agro.agrohack.model.plantCharacteristics.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "plants")
public class Plant {
    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "type")
    private String type;

    @Field(name = "difficulty")
    private Difficulty difficulty;

    @Field(name = "characteristic")
    private PlantCharacteristic characteristic;

    @Field(name = "images")
    private String[] images;

}
