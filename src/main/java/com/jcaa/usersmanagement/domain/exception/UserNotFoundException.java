package com.jcaa.usersmanagement.domain.exception;

public final class UserNotFoundException extends DomainException {

  private UserNotFoundException(final String message) {
    super(message);
  }

  private static final String USER_NOT_FOUND = "The user with id '%s' was not found.";

  public static UserNotFoundException becauseIdWasNotFound(final String userId) {
    // VIOLACIÓN Regla 10: texto de error hardcodeado directamente en el método fábrica.
    // Solucion ahora se usa una constante con nombre descriptivo en lugar de un String literal.
    return new UserNotFoundException(String.format(USER_NOT_FOUND, userId));
  }


}
