package com.example.controller;

import java.util.List;

import com.example.service.BookService;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@Controller("/books")
@AllArgsConstructor
public class BookController {

  private final BookService bookService;

  @Get()
  public Iterable<BookEntity> getBooks() {
    return bookService.findAll();
  }

  @Post()
  public HttpResponse<BookEntity> createBook(@Body @Valid BookEntity book) {
    return HttpResponse.created(bookService.save(book));
  }

  @Get("/{id}")
  public HttpResponse<?> getById(@PathVariable Long id) {
    return bookService.findById(id)
      .map(HttpResponse::ok)
      .orElse(HttpResponse.notFound());
  }

  @Put("/{id}")
  public HttpResponse<?> updateBook(@PathVariable Long id, @Body @Valid BookEntity book) {
    if (bookService.findById(id).isEmpty()) {
      return HttpResponse.notFound();
    }
    
    book.setId(id);
    return HttpResponse.ok(bookService.update(book));
  }

  @Delete("/{id}")
  public HttpResponse<?> deleteBook(@PathVariable Long id) {
    if (bookService.findById(id).isEmpty()) {
      return HttpResponse.notFound();
    }
    
    bookService.deleteById(id);
    return HttpResponse.noContent();
  }
  
  @Get("/search/title")
  public HttpResponse<?> findByTitle(@QueryValue @NotBlank String title) {
    return bookService.findByTitle(title)
      .map(HttpResponse::ok)
      .orElse(HttpResponse.notFound());
  }
  
  @Get("/search/author")
  public HttpResponse<List<BookEntity>> findByAuthor(@QueryValue @NotBlank String author) {
    List<BookEntity> books = bookService.findByAuthor(author);
    return books.isEmpty() 
      ? HttpResponse.notFound() 
      : HttpResponse.ok(books);
  }
}
