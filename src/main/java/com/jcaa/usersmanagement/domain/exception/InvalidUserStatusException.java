package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserStatusException extends DomainException {

  private static final String INVALID_STATUS = "The user status '%s' is not valid.";

  private InvalidUserStatusException(final String message) {
    super(message);
  }

  public static InvalidUserStatusException becauseValueIsInvalid(final String status) {
    // VIOLACIÓN Regla 10: texto hardcodeado AHORA es una constante.
    return new InvalidUserStatusException(String.format(INVALID_STATUS, status));
  }
}
