package com.example.persistence.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;


@MappedEntity("author")
@Data
@NoArgsConstructor
@Serdeable
public class AuthorEntity {

    @GeneratedValue
    @Id
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Nullable
    @Email(message = "Email should be valid")
    private String email;

    @Nullable
    private String website;

    public AuthorEntity(String name, String email, String website) {
        this.name = name;
        this.email = email;
        this.website = website;
    }
} 