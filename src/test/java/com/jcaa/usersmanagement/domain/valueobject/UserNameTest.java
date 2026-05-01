package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidUserNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// VIOLACIÓN Regla 11: se eliminó @DisplayName de la clase y de todos los métodos.
/**
 * Tests para UserName.
 *
 * <p>Cubre: trimming y normalización del valor, NullPointerException cuando el valor es null,
 * e InvalidUserNameException cuando el valor está vacío o no cumple la longitud mínima.
 */
@DisplayName("UserName")
class UserNameTest {

  @ParameterizedTest
  @DisplayName("new UserName() normaliza el valor eliminando espacios en blanco")
  @ValueSource(strings = {"John Arrieta", "   John Arrieta   ", "John Arrieta \t"})
  void shouldValidateUserNameMinimumLength(final String userName) {
    // Arrange
    final String expected = "John Arrieta";
    // Act
    final UserName userNameVo = new UserName(userName);
    // Assert
    assertEquals(expected, userNameVo.toString());
  }

  // -- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("new UserName() lanza NullPointerException cuando el valor es null")
  void shouldValidateUserNameIsNotNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserName(null));
  }

  @ParameterizedTest
  @DisplayName("new UserName() lanza InvalidUserNameException cuando el valor está vacío o no cumple la longitud mínima")
  @ValueSource(
      strings = {"", "  ", "\t", "\n", "\r", "\f", "\b", "Jo", "Ty  ", "", "   Cy ", "Ed\t"})
  void shouldValidateUserNameIsNotEmptyAndMinimumLength(final String userName) {
    // Act & Assert
    assertThrows(InvalidUserNameException.class, () -> new UserName(userName));
  }
}
