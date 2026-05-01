package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidUserStatusException;

public enum UserStatus {
  ACTIVE("Activo"),
  INACTIVE("Inactivo"),
  PENDING("Pendiente de activacion"),
  BLOCKED("Bloqueado");

  private final String displayName;

  UserStatus(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public static UserStatus fromString(final String value) {
    for (final UserStatus status : values()) {
      if (status.name().equalsIgnoreCase(value)) {
        return status;
      }
    }
    throw InvalidUserStatusException.becauseValueIsInvalid(value);
  }
}