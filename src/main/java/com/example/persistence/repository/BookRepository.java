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

 // Podemos usar @Query para especificar una consulta SQL
 @Query("SELECT * FROM book WHERE author = :author")
 List<BookEntity> findByAuthor(String author);
}