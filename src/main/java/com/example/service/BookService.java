package com.example.service;

import jakarta.inject.Singleton;
import com.example.persistence.repository.BookRepository;
import com.example.persistence.model.BookEntity;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class BookService {
  
  private final BookRepository bookRepository;
  
  // CRUD operations
  public Iterable<BookEntity> findAll() {
    return bookRepository.findAll();
  }
  
  public Optional<BookEntity> findById(Long id) {
    return bookRepository.findById(id);
  }
  
  public BookEntity save(BookEntity book) {
    return bookRepository.save(book);
  }
  
  public BookEntity update(BookEntity book) {
    return bookRepository.update(book);
  }
  
  public void deleteById(Long id) {
    bookRepository.deleteById(id);
  }
  
  // Custom repository methods
  public Optional<BookEntity> findByTitle(String title) {
    return bookRepository.findByTitle(title);
  }
  
  public List<BookEntity> findByAuthor(String author) {
    return bookRepository.findByAuthor(author);
  }
}
