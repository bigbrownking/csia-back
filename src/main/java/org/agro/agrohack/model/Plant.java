package org.agro.agrohack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "plants")
public class Plant {
    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "type")
    private String type;

    @Field(name = "water")
    private String water;

    @Field(name = "light")
    private String light;

    @Field(name = "toxicity")
    private String toxicity;

    @Field(name = "humidity")
    private String humidity;

    @Field(name = "common_problems")
    private String common_problems;

    @Field(name = "common_pests")
    private String common_pests;

    @Field(name = "liquid_fertilizing")
    private String liquid_fertilizing;

    @Field(name = )
}
