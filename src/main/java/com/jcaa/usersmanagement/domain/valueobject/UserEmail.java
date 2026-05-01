package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUserEmailException;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public record UserEmail(String value) {

  // VIOLACIÓN Regla 6: logging en capa de dominio (el dominio no debe tener logs)
  private static final Logger LOGGER = Logger.getLogger(UserEmail.class.getName());

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

  public UserEmail {
    final String normalizedValue =
        Objects.requireNonNull(value, "UserEmail cannot be null").trim().toLowerCase();
    // VIOLACIÓN Regla 6: se retiran los logs en capa de dominio

    // Clean Code - Regla 23 (minimizar conocimiento disperso):
    // se elimino la clase de utilities con el metodo de validacion de email
    //ahora esa logica se centra aqui
    validateNotEmpty(normalizedValue);
    validateFormat(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidUserEmailException.becauseValueIsEmpty();
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!EMAIL_PATTERN.matcher(normalizedValue).matches()) {
      throw InvalidUserEmailException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
