package org.agro.agrohack.model;

import lombok.Data;
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

    @Field(name = "class")
    private String class_name;
}
