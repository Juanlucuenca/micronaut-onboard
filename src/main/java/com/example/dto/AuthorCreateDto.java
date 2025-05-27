package com.example.dto;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Serdeable
public class AuthorCreateDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Nullable
    @Email(message = "Email should be valid")
    private String email;

    @Nullable
    private String website;

    public AuthorCreateDto(String name, String email, String website) {
        this.name = name;
        this.email = email;
        this.website = website;
    }
} 