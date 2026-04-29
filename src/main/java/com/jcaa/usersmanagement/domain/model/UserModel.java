package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
// VIOLACIÓN Regla 9 (Hexagonal): el dominio importa una clase de infraestructura.
// Las dependencias siempre deben ir hacia el centro — nunca desde el dominio hacia afuera.
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.UserEntity;

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

  public UserModel activate() {
    return new UserModel(id, name, email, password, role, UserStatus.ACTIVE);
  }

  public UserModel deactivate() {
    return new UserModel(id, name, email, password, role, UserStatus.INACTIVE);
  }

  // Se creo un metodo especifico en el mapper de persistencia de estructura/mapper para encargarse
  // de la conversion de userModel a ENTITY y viceversa
}
