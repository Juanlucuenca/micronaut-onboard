package com.example.dto;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Serdeable
public class BookResponseDto {

    private Long id;
    private String title;

    @Nullable
    private Long authorId;

    @Nullable
    private AuthorBasicDto authorInfo;

    public BookResponseDto(Long id, String title, Long authorId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
    }

    public BookResponseDto(Long id, String title, Long authorId, AuthorBasicDto authorInfo) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorInfo = authorInfo;
    }

    // DTO anidado para información básica del autor
    @Data
    @NoArgsConstructor
    @Serdeable
    public static class AuthorBasicDto {
        private Long id;
        private String name;
        private String email;
        private String website;

        public AuthorBasicDto(Long id, String name, String email, String website) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.website = website;
        }
    }
} 