package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-10s : %s%n";

  private final ConsoleIO console;

  public void print(final UserResponse response) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID",     response.id());
    console.printf(ROW_FORMAT, "Name",   response.name());
    console.printf(ROW_FORMAT, "Email",  response.email());
    console.printf(ROW_FORMAT, "Role",   response.role());
    // Clean Code - Regla 16: se llama al auxiliar que tiene la cadena if/else larga
    console.printf(ROW_FORMAT, "Status", getStatusLabel(response.status()));
    console.println(SEPARATOR);
  }

  public void printList(final List<UserResponse> users) {

    if (users.isEmpty()) {
      console.println("  No users found.");
      return;
    }
    console.printf("%n  Total: %d user(s)%n", users.size());
    users.forEach(this::print);
  }

  // Clean Code - Regla 27 (código listo para leer, no solo para compilar):
  // método simplificado para mejor comprension del usuario y mantenibilidad

  public void printSummary(final List<UserResponse> users) {
    if (users == null || users.isEmpty()) {
      console.println("  No users found.");
      return;
    }

    console.println("  User summary:");
    for (UserResponse user : users) {
      console.printf("    - %s (%s)%n", user.name(), getStatusLabel(user.status()));
    }
  }

  private static String getStatusLabel(final String status) {
    if (status == null) {
      return "Estado desconocido";
    }

    try {
      return UserStatus.valueOf(status).getDisplayName();
    } catch (IllegalArgumentException e) {
      return "Estado desconocido";
    }
  }
}