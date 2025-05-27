package com.example.persistence.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedEntity("book")
@Data
@NoArgsConstructor
@Serdeable
public class BookEntity {

  @GeneratedValue
  @Id
  private Long id;

  @NotBlank(message = "Title is required")
  private String title;

  @Nullable
  @Relation(value = Relation.Kind.MANY_TO_ONE)
  private AuthorEntity authorEntity;

  @Nullable
  private Long authorId;
}
