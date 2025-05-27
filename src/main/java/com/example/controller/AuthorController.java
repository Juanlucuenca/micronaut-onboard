package com.example.controller;

import java.util.List;
import com.example.service.AuthorService;
import com.example.dto.AuthorCreateDto;
import com.example.dto.AuthorResponseDto;
import com.example.dto.AuthorMapper;
import com.example.persistence.model.AuthorEntity;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/authors")
@AllArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED) // Todos los endpoints requieren autenticación
@Tag(name = "Authors", description = "Author management operations")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Get()
    @Operation(summary = "Get all authors", description = "Retrieve all authors from the database")
    public HttpResponse<List<AuthorResponseDto>> getAuthors() {
        Iterable<AuthorEntity> authors = authorService.findAll();
        List<AuthorResponseDto> response = authorMapper.toResponseDtoList(authors);
        return HttpResponse.ok(response);
    }

    @Post()
    @Operation(summary = "Create a new author", description = "Create a new author in the database")
    public HttpResponse<AuthorResponseDto> createAuthor(@Body @Valid AuthorCreateDto authorDto) {
        // Verificar si ya existe un autor con el mismo email
        if (authorDto.getEmail() != null && authorService.existsByEmail(authorDto.getEmail())) {
            return HttpResponse.badRequest();
        }
        
        AuthorEntity entity = authorMapper.toEntity(authorDto);
        AuthorEntity savedEntity = authorService.save(entity);
        AuthorResponseDto response = authorMapper.toResponseDto(savedEntity);
        return HttpResponse.created(response);
    }

    @Get("/{id}")
    @Operation(summary = "Get author by ID", description = "Retrieve a specific author by their ID")
    public HttpResponse<AuthorResponseDto> getById(@PathVariable Long id) {
        return authorService.findById(id)
            .map(authorMapper::toResponseDto)
            .map(HttpResponse::ok)
            .orElse(HttpResponse.notFound());
    }

    @Put("/{id}")
    @Operation(summary = "Update an author", description = "Update an existing author's information")
    public HttpResponse<AuthorResponseDto> updateAuthor(@PathVariable Long id, @Body @Valid AuthorCreateDto authorDto) {
        return authorService.findById(id)
            .map(existingAuthor -> {
                // Verificar si el email ya existe en otro autor
                if (authorDto.getEmail() != null && 
                    !authorDto.getEmail().equals(existingAuthor.getEmail()) &&
                    authorService.existsByEmail(authorDto.getEmail())) {
                    return HttpResponse.<AuthorResponseDto>badRequest();
                }
                
                authorMapper.updateEntityFromDto(existingAuthor, authorDto);
                AuthorEntity updatedEntity = authorService.update(existingAuthor);
                AuthorResponseDto response = authorMapper.toResponseDto(updatedEntity);
                return HttpResponse.ok(response);
            })
            .orElse(HttpResponse.notFound());
    }

    @Delete("/{id}")
    @Operation(summary = "Delete an author", description = "Delete an author from the database")
    public HttpResponse<?> deleteAuthor(@PathVariable Long id) {
        if (authorService.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        
        authorService.deleteById(id);
        return HttpResponse.noContent();
    }

    @Get("/search/name")
    @Operation(summary = "Search authors by name", description = "Find authors whose name contains the specified text")
    public HttpResponse<List<AuthorResponseDto>> findByNameContaining(@QueryValue @NotBlank String name) {
        List<AuthorEntity> authors = authorService.findByNameContaining(name);
        if (authors.isEmpty()) {
            return HttpResponse.notFound();
        }
        
        List<AuthorResponseDto> response = authors.stream()
            .map(authorMapper::toResponseDto)
            .toList();
        return HttpResponse.ok(response);
    }

    @Get("/search/email")
    @Operation(summary = "Find author by email", description = "Find an author by their email address")
    public HttpResponse<AuthorResponseDto> findByEmail(@QueryValue @NotBlank String email) {
        return authorService.findByEmail(email)
            .map(authorMapper::toResponseDto)
            .map(HttpResponse::ok)
            .orElse(HttpResponse.notFound());
    }

    @Get("/ordered")
    @Operation(summary = "Get authors ordered by name", description = "Retrieve all authors ordered alphabetically by name")
    public HttpResponse<List<AuthorResponseDto>> getAuthorsOrderedByName() {
        List<AuthorEntity> authors = authorService.findAllOrderByName();
        List<AuthorResponseDto> response = authors.stream()
            .map(authorMapper::toResponseDto)
            .toList();
        return HttpResponse.ok(response);
    }
} 