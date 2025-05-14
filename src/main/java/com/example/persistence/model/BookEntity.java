package com.example.persistence.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
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

  // Podemos agregar validaciones para los campos con los decoradores de jakarta.validation
  // entre ellos se encuentra @NotBlank, @NotNull, @Min, @Max, @Email, @Pattern, etc.
  @NotBlank(message = "Title is required")
  private String title;

  // Podemos usar @Nullable para indicar que el campo es opcional
  @Nullable 
  private String author;
}
