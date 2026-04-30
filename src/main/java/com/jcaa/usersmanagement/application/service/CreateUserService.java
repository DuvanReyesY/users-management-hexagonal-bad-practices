package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.port.out.SaveUserPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateUserCommand;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Set;

@Log
@RequiredArgsConstructor
public final class CreateUserService implements CreateUserUseCase {

  //clean code 10: se eliminaron comentarios redundantes

  private final SaveUserPort saveUserPort;
  private final GetUserByEmailPort getUserByEmailPort;
  private final EmailNotificationService emailNotificationService;
  private final Validator validator;

  //clean code 2: se reestruscturo el metododo para delegar a otros metodos las diferentes
  //responsabilidades que abarcaba al mismo tiempo originalmente

  @Override
  public UserModel execute(final CreateUserCommand command) {
    validateCommandConstraints(command);
    ensureEmailIsNotTaken(command.email());
    UserModel userToSave = buildUserModel(command);
    UserModel savedUser = saveUserPort.save(userToSave);
    emailNotificationService.notifyUserCreated(savedUser, command.password());
    return savedUser;
  }

  private void validateCommandConstraints(final CreateUserCommand command) {
    Set<ConstraintViolation<CreateUserCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureEmailIsNotTaken(final String email) {
    UserEmail userEmail = new UserEmail(email);
    if (getUserByEmailPort.getByEmail(userEmail).isPresent()) {
      throw UserAlreadyExistsException.becauseEmailAlreadyExists(userEmail.value());
    }
  }

  private UserModel buildUserModel(final CreateUserCommand command) {
    return UserModel.create(
            new UserId(command.id()),
            new UserName(command.name()),
            new UserEmail(command.email()),
            UserPassword.fromPlainText(command.password()),
            UserRole.fromString(command.role())
    );
  }
}