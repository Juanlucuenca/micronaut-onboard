package com.example.dto;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Serdeable
public class AuthorResponseDto {

    private Long id;
    private String name;
    
    @Nullable
    private String email;

    @Nullable
    private String website;

    public AuthorResponseDto(Long id, String name, String email, String website) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.website = website;
    }
} 