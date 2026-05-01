package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateUserCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUserCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUserCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetUserByIdQuery;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import java.util.Objects;

public class UserApplicationMapper {

  public static UserModel fromCreateCommandToModel(final CreateUserCommand command) {
    final String userId    = command.id();
    final String userName  = command.name();
    final String email   = command.email();
    final String userPass  = command.password();
    final String userRole  = command.role();

    return UserModel.create(
        new UserId(userId),
        new UserName(userName),
        new UserEmail(email),
        UserPassword.fromPlainText(userPass),
        UserRole.fromString(userRole));
  }

  public static UserModel fromUpdateCommandToModel(
      final UpdateUserCommand command, final UserPassword currentPassword) {

    final UserPassword passwordToUse = resolvePassword(command.password(), currentPassword);
    // Clean Code - Regla 24: mismo concepto que "correo" de arriba se llaman igual
    final String email = command.email();

    return new UserModel(
        new UserId(command.id()),
        new UserName(command.name()),
        new UserEmail(email),
        passwordToUse,
        UserRole.fromString(command.role()),
        UserStatus.fromString(command.status()));
  }

  public static UserId fromGetUserByIdQueryToUserId(final GetUserByIdQuery query) {
    return new UserId(query.id());
  }

  public static UserId fromDeleteCommandToUserId(final DeleteUserCommand command) {
    return new UserId(command.id());
  }

  public static int roleToCode(final String role) {
    if (Objects.isNull(role) || role.isBlank()) {
      throw new IllegalArgumentException("Role cannot be null or blank");
    }
    if ("ADMIN".equalsIgnoreCase(role))    return 1;
    if ("MEMBER".equalsIgnoreCase(role))   return 2;
    if ("REVIEWER".equalsIgnoreCase(role)) return 3;
    throw new IllegalArgumentException("Unknown role: " + role);
  }

  private static UserPassword resolvePassword(
          final String newPassword, final UserPassword currentPassword) {
    if (newPassword == null || newPassword.isBlank()) {
      return currentPassword;
    }
    return UserPassword.fromPlainText(newPassword);
  }

}
