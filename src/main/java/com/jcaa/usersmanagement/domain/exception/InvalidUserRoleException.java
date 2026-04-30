package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserRoleException extends DomainException {

  private static final String INVALID_ROLE = "The user role '%s' is not valid.";

  private InvalidUserRoleException(final String message) {
    super(message);
  }

  public static InvalidUserRoleException becauseValueIsInvalid(final String role) {
    // VIOLACIÓN Regla 10: texto hardcodeado ahora es una constante.
    return new InvalidUserRoleException(String.format( INVALID_ROLE, role));
  }
}
