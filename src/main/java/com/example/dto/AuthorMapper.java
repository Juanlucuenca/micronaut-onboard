package com.example.dto;

import com.example.persistence.model.AuthorEntity;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class AuthorMapper {

    public AuthorEntity toEntity(AuthorCreateDto dto) {
        return new AuthorEntity(dto.getName(), dto.getEmail(), dto.getWebsite());
    }

    public AuthorResponseDto toResponseDto(AuthorEntity entity) {
        return new AuthorResponseDto(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getWebsite()
        );
    }

    public List<AuthorResponseDto> toResponseDtoList(Iterable<AuthorEntity> entities) {
        return ((List<AuthorEntity>) entities).stream()
            .map(this::toResponseDto)
            .collect(Collectors.toList());
    }

    public void updateEntityFromDto(AuthorEntity entity, AuthorCreateDto dto) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setWebsite(dto.getWebsite());
    }
} 