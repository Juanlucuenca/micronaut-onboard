package com.example.service;

import jakarta.inject.Singleton;
import com.example.persistence.repository.AuthorRepository;
import com.example.persistence.model.AuthorEntity;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class AuthorService {
  
  private final AuthorRepository authorRepository;
  
  // CRUD operations
  public Iterable<AuthorEntity> findAll() {
    return authorRepository.findAll();
  }
  
  public Optional<AuthorEntity> findById(Long id) {
    return authorRepository.findById(id);
  }
  
  public AuthorEntity save(AuthorEntity author) {
    return authorRepository.save(author);
  }
  
  public AuthorEntity update(AuthorEntity author) {
    return authorRepository.update(author);
  }
  
  public void deleteById(Long id) {
    authorRepository.deleteById(id);
  }
  
  // Custom repository methods
  public Optional<AuthorEntity> findByName(String name) {
    return authorRepository.findByName(name);
  }
  
  public List<AuthorEntity> findByNameContaining(String name) {
    return authorRepository.findByNameContaining(name);
  }
  
  public Optional<AuthorEntity> findByEmail(String email) {
    return authorRepository.findByEmail(email);
  }
  
  public List<AuthorEntity> findAllOrderByName() {
    return authorRepository.findAllOrderByName();
  }
  
  // Business logic methods
  public boolean existsByEmail(String email) {
    return findByEmail(email).isPresent();
  }
  
  public boolean existsByName(String name) {
    return findByName(name).isPresent();
  }
} 