package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserNameException extends DomainException {

  private InvalidUserNameException(final String message) {
    super(message);
  }

  private static final String USERNAME_EMPTY = "The user name must not be empty.";
  private static final String MINIMUM_LENGHT = "The user name must have at least %d characters.";

  public static InvalidUserNameException becauseValueIsEmpty() {

    return new InvalidUserNameException(USERNAME_EMPTY);
  }

  public static InvalidUserNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUserNameException(
        String.format(MINIMUM_LENGHT, minimumLength));
  }
}
