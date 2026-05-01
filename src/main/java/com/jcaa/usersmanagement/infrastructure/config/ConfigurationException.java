package com.jcaa.usersmanagement.infrastructure.config;

public final class ConfigurationException extends RuntimeException {

  private static final String FAILED_APP_CONF = "Failed to load the application configuration.";

  private ConfigurationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static ConfigurationException becauseLoadFailed(final Throwable cause) {
    // VIOLACIÓN Regla 10: Se quito el texto de error hardcodeado directamente.
    // y se agrego una constante con nombre descriptivo
    return new ConfigurationException(FAILED_APP_CONF, cause);
  }
}
