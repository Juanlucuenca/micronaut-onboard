package com.example.controller;

import java.util.List;
import com.example.service.BookService;
import com.example.dto.BookCreateDto;
import com.example.dto.BookResponseDto;
import com.example.dto.BookMapper;
import com.example.persistence.model.BookEntity;
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

@Controller("/books")
@AllArgsConstructor
@Secured(SecurityRule.IS_ANONYMOUS) // GET endpoints son públicos
@Tag(name = "Books", description = "Book management operations")
public class BookController {

  private final BookService bookService;
  private final BookMapper bookMapper;

  @Get()
  @Operation(summary = "Get all books", description = "Retrieve all books from the database")
  public HttpResponse<List<BookResponseDto>> getBooks() {
    Iterable<BookEntity> books = bookService.findAll();
    List<BookResponseDto> response = bookMapper.toResponseDtoList(books);
    return HttpResponse.ok(response);
  }

  @Post()
  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Operation(summary = "Create a new book", description = "Create a new book in the database")
  public HttpResponse<BookResponseDto> createBook(@Body @Valid BookCreateDto bookDto) {
    try {
      // Verificar si ya existe un libro con el mismo título
      if (bookService.existsByTitle(bookDto.getTitle())) {
        return HttpResponse.badRequest();
      }
      
      BookEntity entity = bookMapper.toEntity(bookDto);
      BookEntity savedEntity = bookService.save(entity);
      
      BookResponseDto response = bookMapper.toResponseDtoWithAuthor(savedEntity);
      return HttpResponse.created(response);
    } catch (IllegalArgumentException e) {
      // Error de validación (ej: autor no existe)
      return HttpResponse.badRequest();
    }
  }

  @Get("/{id}")
  @Operation(summary = "Get book by ID", description = "Retrieve a specific book by its ID")
  public HttpResponse<BookResponseDto> getById(@PathVariable Long id) {
    return bookService.findById(id)
      .map(bookMapper::toResponseDtoWithAuthor)
      .map(HttpResponse::ok)
      .orElse(HttpResponse.notFound());
  }

  @Put("/{id}")
  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Operation(summary = "Update a book", description = "Update an existing book's information")
  public HttpResponse<BookResponseDto> updateBook(@PathVariable Long id, @Body @Valid BookCreateDto bookDto) {
    return bookService.findById(id)
      .map(existingBook -> {
        try {
          // Verificar si el título ya existe en otro libro
          if (!bookDto.getTitle().equals(existingBook.getTitle()) &&
              bookService.existsByTitle(bookDto.getTitle())) {
            return HttpResponse.<BookResponseDto>badRequest();
          }
          
          bookMapper.updateEntityFromDto(existingBook, bookDto);
          BookEntity updatedEntity = bookService.update(existingBook);
          
          BookResponseDto response = bookMapper.toResponseDtoWithAuthor(updatedEntity);
          return HttpResponse.ok(response);
        } catch (IllegalArgumentException e) {
          // Error de validación (ej: autor no existe)
          return HttpResponse.<BookResponseDto>badRequest();
        }
      })
      .orElse(HttpResponse.notFound());
  }

  @Delete("/{id}")
  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Operation(summary = "Delete a book", description = "Delete a book from the database")
  public HttpResponse<?> deleteBook(@PathVariable Long id) {
    if (!bookService.canDeleteBook(id)) {
      return HttpResponse.notFound();
    }
    
    bookService.deleteById(id);
    return HttpResponse.noContent();
  }
  
  @Get("/search/title")
  @Operation(summary = "Find book by title", description = "Find a book by its exact title")
  public HttpResponse<BookResponseDto> findByTitle(@QueryValue @NotBlank String title) {
    return bookService.findByTitle(title)
      .map(bookMapper::toResponseDtoWithAuthor)
      .map(HttpResponse::ok)
      .orElse(HttpResponse.notFound());
  }

  @Get("/search/author-id/{authorId}")
  @Operation(summary = "Find books by author ID", description = "Find all books by author ID (relation)")
  public HttpResponse<List<BookResponseDto>> findByAuthorId(@PathVariable Long authorId) {
    List<BookEntity> books = bookService.findByAuthorId(authorId);
    if (books.isEmpty()) {
      return HttpResponse.notFound();
    }
    
    List<BookResponseDto> response = bookMapper.toResponseDtoListWithAuthor(books);
    return HttpResponse.ok(response);
  }

  @Get("/search/author-name")
  @Operation(summary = "Find books by author entity name", description = "Find books using author entity relationship")
  public HttpResponse<List<BookResponseDto>> findByAuthorEntityName(@QueryValue @NotBlank String authorName) {
    List<BookEntity> books = bookService.findByAuthorEntityName(authorName);
    if (books.isEmpty()) {
      return HttpResponse.notFound();
    }
    
    List<BookResponseDto> response = bookMapper.toResponseDtoListWithAuthor(books);
    return HttpResponse.ok(response);
  }

  @Get("/with-author")
  @Operation(summary = "Get books with author", description = "Get all books that have an author assigned")
  public HttpResponse<List<BookResponseDto>> getBooksWithAuthor() {
    List<BookEntity> books = bookService.findBooksWithAuthor();
    List<BookResponseDto> response = bookMapper.toResponseDtoListWithAuthor(books);
    return HttpResponse.ok(response);
  }

  @Get("/without-author")
  @Operation(summary = "Get books without author", description = "Get all books that don't have an author assigned")
  public HttpResponse<List<BookResponseDto>> getBooksWithoutAuthor() {
    List<BookEntity> books = bookService.findBooksWithoutAuthor();
    List<BookResponseDto> response = bookMapper.toResponseDtoList(books);
    return HttpResponse.ok(response);
  }

  @Get("/stats/author/{authorId}")
  @Operation(summary = "Count books by author", description = "Get the count of books by a specific author")
  public HttpResponse<Long> countByAuthorId(@PathVariable Long authorId) {
    Long count = bookService.countByAuthorId(authorId);
    return HttpResponse.ok(count);
  }
}
