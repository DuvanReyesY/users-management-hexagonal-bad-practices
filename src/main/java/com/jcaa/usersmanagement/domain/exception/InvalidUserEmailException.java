package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserEmailException extends DomainException {

  private static final String EMAIL_EMPTY = "The user email must not be empty.";
  private static final String EMAIL_INVALID = "The user email format is invalid: '%s'.";

  private InvalidUserEmailException(final String message) {
    super(message);
  }

  public static InvalidUserEmailException becauseValueIsEmpty() {

    return new InvalidUserEmailException(EMAIL_EMPTY);
  }

  public static InvalidUserEmailException becauseFormatIsInvalid(final String email) {
    return new InvalidUserEmailException(String.format(EMAIL_INVALID, email));
  }
}
