package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

// VIOLACIÓN Regla 10: todos los mensajes de error fueron reemplazados con
// constantes con nombre descriptivo en la clase.
public final class PersistenceException extends RuntimeException {

  private static final String SAVE_FAILED = "Failed to save user with ID: '%s'.";

  private static final String UPDATE_FAILED = "Failed to update user with ID: '%s'.";

  private static final String USER_NOT_FOUND = "Failed to find user with ID: '%s'.";

  private static final String USER_NOT_FOUND_BY_EMAIL = "Failed to find user with email: '%s'.";

  private static final String ALL_USERS_NOT_FOUND = "Failed to retrieve all users.";

  private static final String DELETE_USER_FAILED = "Failed to delete user with ID: '%s'.";

  private static final String CONECTION_FAILED = "Could not establish database connection.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(SAVE_FAILED, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(UPDATE_FAILED, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(USER_NOT_FOUND, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
      final String email, final Throwable cause) {
    return new PersistenceException(String.format(USER_NOT_FOUND_BY_EMAIL, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(ALL_USERS_NOT_FOUND, cause);
  }

  public static PersistenceException becauseDeleteFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(DELETE_USER_FAILED, userId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(CONECTION_FAILED, cause);
  }
}
