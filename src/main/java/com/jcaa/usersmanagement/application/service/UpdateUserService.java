package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateUserUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateUserPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUserCommand;
import com.jcaa.usersmanagement.application.service.mapper.UserApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Optional;
import java.util.Set;

@Log
@RequiredArgsConstructor
public final class UpdateUserService implements UpdateUserUseCase {

  private final UpdateUserPort updateUserPort;
  private final GetUserByIdPort getUserByIdPort;
  private final GetUserByEmailPort getUserByEmailPort;
  private final EmailNotificationService emailNotificationService;
  private final Validator validator;

  @Override
  public void execute(final UpdateUserCommand command) {
    // Clean Code - Regla 8 (separar comandos y consultas — CQS):
    // se cambia el metodo a void
    validateCommand(command);
    final UserId userId = new UserId(command.id());
    final UserModel current = findExistingUserOrFail(userId);
    final UserEmail newEmail = new UserEmail(command.email());
    ensureEmailIsNotTakenByAnotherUser(newEmail, userId);
    final UserModel userToUpdate = UserApplicationMapper.fromUpdateCommandToModel(command, current.getPassword());
    updateUserPort.update(userToUpdate);
    emailNotificationService.notifyUserUpdated(userToUpdate);
  }

  // Clean Code - Regla 6: se crearon 2 metodos aparte sin
  // que un boolean decida el comportamiento interno.

  private void notifyUserUpdated(final UserModel user) {
    emailNotificationService.notifyUserUpdated(user);
  }

  private void validateCommand(final UpdateUserCommand command) {
    final Set<ConstraintViolation<UpdateUserCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private UserModel findExistingUserOrFail(final UserId userId) {
    return getUserByIdPort
        .getById(userId)
        .orElseThrow(() -> UserNotFoundException.becauseIdWasNotFound(userId.value()));
  }

    // Clean Code - Regla 17: condición booleana excesivamente larga eliminada se separo en
    //  2 metodos
    // Clean Code - Regla 27 (código listo para leer, no solo para ejecutar):
    // Sin explicación oral del autor es imposible determinar qué condición exacta
    // se está evaluando ni por qué hay lógica redundante en la segunda mitad del OR.

  private void ensureEmailIsNotTakenByAnotherUser(final UserEmail newEmail, final UserId ownerId) {
    Optional<UserModel> existingUser = getUserByEmailPort.getByEmail(newEmail);

    if (existingUser.isPresent() && isNotSameUser(existingUser.get(), ownerId)) {
      throw UserAlreadyExistsException.becauseEmailAlreadyExists(newEmail.value());
    }
  }

  private boolean isNotSameUser(UserModel existingUser, UserId ownerId) {
    return !existingUser.getId().equals(ownerId);
  }
}
