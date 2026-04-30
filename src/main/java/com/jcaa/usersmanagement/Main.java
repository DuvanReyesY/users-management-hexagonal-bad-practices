package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Logger;

// Clean Code - Regla 24 (consistencia semántica):
// Todo el proyecto usa java.util.logging.Logger (vía @Log de Lombok o Logger.getLogger()),
// pero esta clase usa org.slf4j.Logger + LoggerFactory de una librería diferente.
// El mismo concepto —"logger de la aplicación"— se resuelve con dos frameworks distintos
// sin justificación. Un lector no puede saber cuál es el estándar del proyecto.
// La regla dice: las mismas ideas deben resolverse igual en todo el proyecto.
//
// Clean Code - Regla 22 (código difícil de borrar y refactorizar):
// main() está acoplado directamente a tres clases concretas: DependencyContainer,
// UserManagementCli y ConsoleIO. Si se quiere reemplazar cualquiera de ellas
// (p. ej., cambiar el entrypoint de CLI a GUI), hay que editar el punto de entrada
// de la aplicación. No hay ninguna abstracción que proteja este acoplamiento.
public final class Main {

  private static final Logger log = Logger.getLogger(Main.class.getName());

  // Clean Code - Regla 1 (una sola cosa por función):
  // main() ya no hace mas de una sola cosa y se crearon los respectivos metodos:
  //   buildContainer(), buildConsole(), buildCli().

  public static void main(final String[] args) {
    log.info("Starting Users Management System...");
    final UserController controller = buildContainer();
    try (final Scanner scanner = new Scanner(System.in)) {
      final UserManagementCli cli = buildCli(controller, scanner);
      cli.start();
    }
  }

  private static UserController buildContainer() {
    return new DependencyContainer().userController();
  }

  private static ConsoleIO buildConsole(final Scanner scanner) {
    return new ConsoleIO(scanner, System.out);
  }

  private static UserManagementCli buildCli(final UserController controller, final Scanner scanner) {
    return new UserManagementCli(controller, buildConsole(scanner));
  }

}