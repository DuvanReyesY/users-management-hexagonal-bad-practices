package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.service.dto.command.LoginCommand;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.exception.InvalidCredentialsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class LoginService implements LoginUseCase {

  private final GetUserByEmailPort getUserByEmailPort;
  private final Validator validator;

  @Override
  public UserModel execute(final LoginCommand command) {
    validateCommand(command);
    UserModel user = findUserByEmail(command.email());
    verifyPassword(user, command.password());
    verifyUserIsActive(user);
    return user;

    // Clean Code - Regla 8: violación CQS — el método se llama "getAndValidateUser"
    // pero además de consultar, tiene efectos secundarios (logs internos, acumula estado implícito).
    // Un método que consulta información no debe modificar estado.
  }

  // Clean Code - Regla 8: viola CQS — consulta Y tiene efectos de modificación implícitos.
  // Clean Code - Regla 1: el metodo se separo en varios metodos cada uno ocupandose de una sola funcionalidad.
  // Clean Code - Regla 2 (funciones cortas): se reestruscturo el metododo para delegar a otros metodos las diferentes
  // responsabilidades que abarcaba al mismo tiempo originalmente

  // Clean Code - Regla 14 (Ley de Deméter): se navega a internals del objeto:
  //   user → getPassword() → verifyPlain() en lugar de delegar con user.passwordMatches(plain).


    // Clean Code - Regla 12 (alta cohesión): lógica de dominio sobre estados válidos
    // dispersa en la capa de aplicación — debería encapsularse en UserModel o un servicio de dominio.
    // Clean Code - Regla 17: condición booleana compleja y difícil de leer.
    // La regla dice: extraer condiciones complejas a métodos con nombre significativo.
    // Esta expresión equivale a "user.getStatus() != ACTIVE" pero está escrita de forma
    // redundante e innecesariamente larga — el lector debe analizar cada rama para
    // deducir la intención central. Debería ser: if (!user.isAllowedToLogin()).


  private UserModel findUserByEmail(final String email) {
    return getUserByEmailPort.getByEmail(new UserEmail(email))
            .orElseThrow(InvalidCredentialsException::becauseCredentialsAreInvalid);
  }
// Clean Code - Regla 14: acceso profundo a internals del value object.
  private void verifyPassword(final UserModel user, final String plainPassword) {
    if (!user.getPassword().verifyPlain(plainPassword)) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
  }
//el lector debe analizar cada rama para
// deducir la intención central. Debería ser: if (!user.isAllowedToLogin()).
  private void verifyUserIsActive(final UserModel user) {
    if (user.getStatus() != UserStatus.ACTIVE) {
      throw InvalidCredentialsException.becauseUserIsNotActive();
    }
  }

  private void validateCommand(final LoginCommand command) {
    final Set<ConstraintViolation<LoginCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
