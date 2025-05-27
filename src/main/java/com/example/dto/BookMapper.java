package com.example.dto;

import com.example.persistence.model.BookEntity;
import com.example.persistence.model.AuthorEntity;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class BookMapper {

    public BookEntity toEntity(BookCreateDto dto) {
        BookEntity entity = new BookEntity();
        entity.setTitle(dto.getTitle());
        entity.setAuthorId(dto.getAuthorId());
        return entity;
    }

    public BookResponseDto toResponseDto(BookEntity entity) {
        return new BookResponseDto(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthorId()
        );
    }

    public BookResponseDto toResponseDtoWithAuthor(BookEntity entity) {
        BookResponseDto.AuthorBasicDto authorInfo = null;
        
        if (entity.getAuthorEntity() != null) {
            AuthorEntity author = entity.getAuthorEntity();
            authorInfo = new BookResponseDto.AuthorBasicDto(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getWebsite()
            );
        }

        return new BookResponseDto(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthorId(),
            authorInfo
        );
    }

    public List<BookResponseDto> toResponseDtoList(Iterable<BookEntity> entities) {
        return ((List<BookEntity>) entities).stream()
            .map(this::toResponseDto)
            .collect(Collectors.toList());
    }

    public List<BookResponseDto> toResponseDtoListWithAuthor(List<BookEntity> entities) {
        return entities.stream()
            .map(this::toResponseDtoWithAuthor)
            .collect(Collectors.toList());
    }

    public void updateEntityFromDto(BookEntity entity, BookCreateDto dto) {
        entity.setTitle(dto.getTitle());
        entity.setAuthorId(dto.getAuthorId());
    }

    // Método para crear un DTO básico de autor desde AuthorEntity
    public BookResponseDto.AuthorBasicDto toAuthorBasicDto(AuthorEntity author) {
        if (author == null) {
            return null;
        }
        return new BookResponseDto.AuthorBasicDto(
            author.getId(),
            author.getName(),
            author.getEmail(),
            author.getWebsite()
        );
    }
} 