package com.example.model;


import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Serdeable.Serializable
@Serdeable.Deserializable
public class Actor {

    private Long id;
    private String firstName;
    private String lastName;
    private int rating;
    private boolean oscarPrized;

}

