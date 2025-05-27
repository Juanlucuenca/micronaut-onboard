package com.example.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.example.persistence.model.BookEntity;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface BookRepository extends CrudRepository<BookEntity, Long> {

  // Podemos generar una consulta SQL personalizada en el ORM
  // con el nombre de un metodo
 Optional<BookEntity> findByTitle(String title);

 // Métodos para trabajar con la relación Author
 List<BookEntity> findByAuthorId(Long authorId);

 @Query("SELECT b.* FROM book b JOIN author a ON b.author_id = a.id WHERE a.name = :authorName")
 List<BookEntity> findByAuthorEntityName(String authorName);

 @Query("SELECT COUNT(*) FROM book WHERE author_id = :authorId")
 Long countByAuthorId(Long authorId);

 @Query("SELECT b.* FROM book b WHERE b.author_id IS NOT NULL")
 List<BookEntity> findBooksWithAuthor();

 @Query("SELECT b.* FROM book b WHERE b.author_id IS NULL")
 List<BookEntity> findBooksWithoutAuthor();
}