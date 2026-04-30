package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserPasswordException extends DomainException {

  private static final String MINIMUM_LENGTH = "The user name must have at least %d characters.";

  private static final String PASSWORD_EMPTY = "The user password must not be empty.";

  private InvalidUserPasswordException(final String message) {
    super(message);
  }

  public static InvalidUserPasswordException becauseValueIsEmpty() {
    return new InvalidUserPasswordException(PASSWORD_EMPTY);
  }

  public static InvalidUserPasswordException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUserPasswordException(
        String.format(MINIMUM_LENGTH, minimumLength));
  }
}
