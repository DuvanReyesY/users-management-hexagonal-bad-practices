package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;

// VIOLACIÓN Regla 9 (Hexagonal): el dominio importaba una clase de infraestructura pero ya se elimino
//la referencia

import lombok.AllArgsConstructor;
import lombok.Value;

// se agrego @Value y se dejo @AllArgsConstructor de lombok para hacer uso de todos los parametros en el constructor
// con esto solucionamos la violacion 2 y la 15 de clean code del uso de data que permitia la modificacion de los valores por setters
// desde fuera del dominio, rompiendo el encapsulamiento.

@Value
@AllArgsConstructor
public class UserModel {

  UserId id;
  UserName name;
  UserEmail email;
  UserPassword password;
  UserRole role;
  UserStatus status;


  public static UserModel create(
          final UserId id,
          final UserName name,
          final UserEmail email,
          final UserPassword password,
          final UserRole role) {
    return new UserModel(id, name, email, password, role, UserStatus.PENDING);
  }

  public String getIdValue() {
    return id.value();
  }

  public String getNameValue() {
    return name.value();
  }

  public String getEmailValue() {
    return email.value();
  }

  public String getPasswordHash() {
    return password.value();
  }


  public boolean verifyPassword(String plainPassword) {
    return password.verifyPlain(plainPassword);
  }

  public boolean isActive() {
    return this.status == UserStatus.ACTIVE;
  }

  public boolean isAdmin() {
    return this.role == UserRole.ADMIN;
  }

  public UserModel activate() {
    return new UserModel(id, name, email, password, role, UserStatus.ACTIVE);
  }

  public UserModel deactivate() {
    return new UserModel(id, name, email, password, role, UserStatus.INACTIVE);
  }

  // el mapper en infrastructure/persistence/mapper debe encargarse
  // de la conversion de userModel a ENTITY y viceversa
}
