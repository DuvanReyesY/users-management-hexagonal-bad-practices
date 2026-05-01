package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidUserIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// VIOLACIÓN Regla 11: se resolvio
/**
 * Tests para UserId.
 *
 * <p>Cubre: trimming del valor, NullPointerException cuando el valor es null,
 * e InvalidUserIdException cuando el valor está vacío o en blanco.
 */
@DisplayName("UserId")
class UserIdTest {

  @ParameterizedTest
  @DisplayName("new UserId() normaliza el valor eliminando espacios en blanco")
  @ValueSource(strings = {" user123 ", "  user123  ", "user123\t"})
  void shouldCreateUserIdWithTrimmedValue(final String input) {
    // Arrange
    final String expected = "user123";
    // Act
    final UserId userId = new UserId(input);
    // Assert
    assertEquals(expected, userId.toString());
  }

  @Test
  @DisplayName("new UserId() lanza NullPointerException cuando el valor es null")
  void shouldThrowNullPointerExceptionWhenUserIdIsNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserId(null));
  }

  @ParameterizedTest
  @DisplayName("new UserId() lanza InvalidUserIdException cuando el valor está vacío o en blanco")
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  void shouldThrowIllegalArgumentExceptionWhenUserIdIsEmpty(String input) {
    // Act & Assert
    assertThrows(InvalidUserIdException.class, () -> new UserId(input));
  }
}
