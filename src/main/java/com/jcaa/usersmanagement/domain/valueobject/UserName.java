package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUserNameException;

import java.util.Objects;

public record UserName(String value) {

  private static final int MINIMUM_LENGTH = 3;

  // VIOLACIÓN Regla 10: se eliminó la constante MINIMUM_LENGTH — se usa magic number directamente
  public UserName {
    // VIOLACIÓN Regla 4: se usa Objects.isNull()  en lugar de == null.

    if (Objects.isNull(value)) {
      throw new NullPointerException("UserName cannot be null");
    }
    final String normalizedValue = value.trim();
    validateNotEmpty(normalizedValue);
    validateMinimumLength(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidUserNameException.becauseValueIsEmpty();
    }
  }

  private static void validateMinimumLength(final String normalizedValue) {
    // VIOLACIÓN Regla 10: se usa una constante con nombre descriptivo "MINIMUM_LENGTH"

    if (normalizedValue.length() < MINIMUM_LENGTH) {
      throw InvalidUserNameException.becauseLengthIsTooShort(3);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
