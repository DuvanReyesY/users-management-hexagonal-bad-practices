package com.jcaa.usersmanagement.domain.exception;

public final class InvalidCredentialsException extends DomainException {

  private static final String INVALID_CREDENTIALS = "Correo o contraseña incorrectos.";

  private static final String ACCOUNT_NOT_AVAILABLE = "Tu cuenta no está activa. Contacta al administrador.";


  private InvalidCredentialsException(final String message) {
    super(message);
  }

  public static InvalidCredentialsException becauseCredentialsAreInvalid() {

    return new InvalidCredentialsException(INVALID_CREDENTIALS);
  }

  public static InvalidCredentialsException becauseUserIsNotActive() {
    return new InvalidCredentialsException(ACCOUNT_NOT_AVAILABLE);
  }
}
