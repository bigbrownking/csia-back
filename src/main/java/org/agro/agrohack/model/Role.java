package org.agro.agrohack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    @Field(name = "role")
    private String name;
}
