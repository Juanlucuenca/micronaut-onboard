package com.example;

import java.util.Optional;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

@Controller("/books")
public class BookController {

  private final BookRepository bookRepository;

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Get()
  public Iterable<BookEntity> getBooks() {
    return bookRepository.findAll();
  }

  @Post()
  public BookEntity createBook(@Body BookEntity book) {
    return bookRepository.save(book);
  }

  @Get("/{id}")
  public Optional<BookEntity> getById(@PathVariable Long id) {
    return bookRepository.findById(id);
  }

  @Put("/{id}")
  public BookEntity updateBook(@PathVariable Long id, @Body BookEntity book) {
    return bookRepository.update(book);
  }

  @Delete("/{id}")
  public void deleteBook(@PathVariable Long id) {
    bookRepository.deleteById(id);
  }

  }
