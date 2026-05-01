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
  }

  // Clean Code - Regla 8: metodo eliminado y correcciones aplicadas
  // Clean Code - Regla 1: el metodo se separo en varios metodos cada uno ocupandose de una sola funcionalidad.
  // Clean Code - Regla 2 (funciones cortas): se reestruscturo el metododo para delegar a otros metodos las diferentes
  // responsabilidades que abarcaba al mismo tiempo originalmente

    // Clean Code - Regla 12 (alta cohesión): resuelto ya que todos los metodos presentes siguen un mismo hilo
    //login de un usuario
    // Clean Code - Regla 17: condición booleana compleja y difícil de leer.
    // La regla dice: extraer condiciones complejas a métodos con nombre significativo.
    // Esta expresión equivale a "user.getStatus() != ACTIVE" pero está escrita de forma
    // redundante e innecesariamente larga — el lector debe analizar cada rama para
    // deducir la intención central. Debería ser: if (!user.isAllowedToLogin()).


  private UserModel findUserByEmail(final String email) {
    return getUserByEmailPort.getByEmail(new UserEmail(email))
            .orElseThrow(InvalidCredentialsException::becauseCredentialsAreInvalid);
  }
// Clean Code - Regla 14: se delega al value object
  private void verifyPassword(final UserModel user, final String plainPassword) {
    if (!user.verifyPassword(plainPassword)) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
  }

  private void verifyUserIsActive(final UserModel user) {
    if (user.isActive()) {
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
