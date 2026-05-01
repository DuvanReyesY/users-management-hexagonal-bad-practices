package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import lombok.experimental.UtilityClass;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// VIOLACIÓN Regla 4: solved @UtilityClass para evitar instanciación accidental implementada.
@UtilityClass
public class DatabaseConnectionFactory {

  public static Connection createConnection(final DatabaseConfig config) {
    // VIOLACIÓN Regla 4: solved método que no usa estado de instancia ya
    //  está declarado como static.
    try {
      return DriverManager.getConnection(
          config.buildJdbcUrl(), config.username(), config.password());
    } catch (final SQLException exception) {
      throw PersistenceException.becauseConnectionFailed(exception);
    }
  }
}
