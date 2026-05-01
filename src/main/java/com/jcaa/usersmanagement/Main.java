package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.CliApp;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Logger;

public final class Main {

  private static final Logger log = Logger.getLogger(Main.class.getName());

  // Clean Code - Regla 1 (una sola cosa por función):
  // main() ya no hace mas de una sola cosa y se crearon los respectivos metodos:
  //   buildContainer(), buildConsole(), buildCli().

  public static void main(final String[] args) {
    log.info("Starting Users Management System...");

    try {
      final DependencyContainer container = new DependencyContainer();
      final UserController controller = container.userController();

      final CliApp cli = createCliApp(controller);
      cli.start();

      log.info("Users Management System finished successfully");
    } catch (Exception e) {
      log.severe("Fatal error: " + e.getMessage());
      System.exit(1);
    }
  }

  private static CliApp createCliApp(final UserController controller) {
    final ConsoleIO console = buildConsole();
    return new UserManagementCli(controller, console);
  }

  private static ConsoleIO buildConsole() {
    return new ConsoleIO(new Scanner(System.in), System.out);
  }

}