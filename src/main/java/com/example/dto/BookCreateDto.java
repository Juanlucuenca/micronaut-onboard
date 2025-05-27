package com.example.dto;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Serdeable
public class BookCreateDto {

    @NotBlank(message = "Title is required")
    private String title;

    @Nullable
    private Long authorId;

    public BookCreateDto(String title, Long authorId) {
        this.title = title;
        this.authorId = authorId;
    }
} 