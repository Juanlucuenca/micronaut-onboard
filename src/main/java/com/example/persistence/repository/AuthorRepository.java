package com.example.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.example.persistence.model.AuthorEntity;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findByName(String name);

    @Query("SELECT * FROM author WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<AuthorEntity> findByNameContaining(String name);

    Optional<AuthorEntity> findByEmail(String email);

    @Query("SELECT * FROM author ORDER BY name ASC")
    List<AuthorEntity> findAllOrderByName();
} 