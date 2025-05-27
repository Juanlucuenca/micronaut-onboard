package com.example.service;

import jakarta.inject.Singleton;
import com.example.persistence.repository.BookRepository;
import com.example.persistence.model.BookEntity;
import com.example.service.AuthorService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class BookService {
  
  private final BookRepository bookRepository;
  private final AuthorService authorService;
  
  // CRUD operations
  public Iterable<BookEntity> findAll() {
    return bookRepository.findAll();
  }
  
  public Optional<BookEntity> findById(Long id) {
    return bookRepository.findById(id);
  }
  
  public BookEntity save(BookEntity book) {
    // Validar que el autor existe si se proporciona authorId
    if (book.getAuthorId() != null) {
      validateAuthorExists(book.getAuthorId());
      // Cargar la entidad del autor para la relación
      authorService.findById(book.getAuthorId())
        .ifPresent(book::setAuthorEntity);
    }
    return bookRepository.save(book);
  }
  
  public BookEntity update(BookEntity book) {
    // Validar que el autor existe si se proporciona authorId
    if (book.getAuthorId() != null) {
      validateAuthorExists(book.getAuthorId());
      // Cargar la entidad del autor para la relación
      authorService.findById(book.getAuthorId())
        .ifPresent(book::setAuthorEntity);
    }
    return bookRepository.update(book);
  }
  
  public void deleteById(Long id) {
    bookRepository.deleteById(id);
  }
  
  // Custom repository methods
  public Optional<BookEntity> findByTitle(String title) {
    return bookRepository.findByTitle(title);
  }

  // Métodos para trabajar con la relación Author
  public List<BookEntity> findByAuthorId(Long authorId) {
    return bookRepository.findByAuthorId(authorId);
  }

  public List<BookEntity> findByAuthorEntityName(String authorName) {
    return bookRepository.findByAuthorEntityName(authorName);
  }

  public Long countByAuthorId(Long authorId) {
    return bookRepository.countByAuthorId(authorId);
  }

  public List<BookEntity> findBooksWithAuthor() {
    return bookRepository.findBooksWithAuthor();
  }

  public List<BookEntity> findBooksWithoutAuthor() {
    return bookRepository.findBooksWithoutAuthor();
  }

  // Business logic methods
  public boolean existsByTitle(String title) {
    return findByTitle(title).isPresent();
  }

  public boolean canDeleteBook(Long bookId) {
    // Aquí se pueden agregar validaciones adicionales
    // Por ejemplo, verificar si el libro está en préstamo, etc.
    return findById(bookId).isPresent();
  }

  // Método para validar que el autor existe
  private void validateAuthorExists(Long authorId) {
    if (authorService.findById(authorId).isEmpty()) {
      throw new IllegalArgumentException("Author with ID " + authorId + " does not exist");
    }
  }
}
