package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests para EmailDestinationModel.
 *
 * <p>Verifica el camino feliz (todos los getters) y los cuatro puntos de validación del
 * constructor: cada campo tiene su propia llamada a {@code validateNotBlank}, por lo que se
 * necesita un test por campo para alcanzar esa instrucción y ejercer las dos ramas (null y blank).
 */
@DisplayName("EmailDestinationModel")
class EmailDestinationModelTest {

  // ── Arrange
  private static final String EMAIL = "dest@example.com";
  private static final String NAME = "Recipient Name";
  private static final String SUBJECT = "Welcome";
  private static final String BODY = "Hello, welcome to the platform.";

  // ── Happy path

  @Test
  @DisplayName("Constructor debe preservar los cuatro campos cuando los datos son válidos")
  void shouldCreateModelWithAllValidFields() {
    // Arrange + Act
    final EmailDestinationModel model = new EmailDestinationModel(EMAIL, NAME, SUBJECT, BODY);

    // Assert
    assertAll(
        "campos de EmailDestinationModel",
        () -> assertEquals(EMAIL, model.getDestinationEmail(), "destinationEmail"),
        () -> assertEquals(NAME, model.getDestinationName(), "destinationName"),
        () -> assertEquals(SUBJECT, model.getSubject(), "subject"),
        () -> assertEquals(BODY, model.getBody(), "body"));
  }


  @Test
  @DisplayName("Constructor debe lanzar NullPointerException cuando destinationEmail es null")
  void shouldThrowNpeWhenDestinationEmailIsNull() {
    // Act & Assert
    assertThrows(
        NullPointerException.class, () -> new EmailDestinationModel(null, NAME, SUBJECT, BODY));
  }

  @Test
  @DisplayName(
      "Constructor debe lanzar IllegalArgumentException cuando destinationName está en blanco")
  void shouldThrowIaeWhenDestinationNameIsBlank() {
    // Act & Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> new EmailDestinationModel(EMAIL, "   ", SUBJECT, BODY));
  }

  @Test
  @DisplayName("Constructor debe lanzar NullPointerException cuando subject es null")
  void shouldThrowNpeWhenSubjectIsNull() {
    // Act & Assert
    assertThrows(
        NullPointerException.class, () -> new EmailDestinationModel(EMAIL, NAME, null, BODY));
  }

  @Test
  @DisplayName("Constructor debe lanzar IllegalArgumentException cuando body está vacío")
  void shouldThrowIaeWhenBodyIsEmpty() {
    // Act & Assert
    assertThrows(
        IllegalArgumentException.class, () -> new EmailDestinationModel(EMAIL, NAME, SUBJECT, ""));
  }
}
