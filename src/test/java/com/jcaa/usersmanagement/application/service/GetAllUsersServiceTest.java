package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllUsersPort;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests para GetAllUsersService.
 *
 * <p>Cubre: retorno correcto de la lista de usuarios cuando el puerto devuelve datos,
 * y retorno de lista vacía cuando no hay usuarios registrados.
 */

@DisplayName("GetAllUsersService")
@ExtendWith(MockitoExtension.class)
class GetAllUsersServiceTest {

  @Mock private GetAllUsersPort getAllUsersPort;

  private GetAllUsersService service;

  @BeforeEach
  void setUp() {
    service = new GetAllUsersService(getAllUsersPort);
  }

  @Test
  @DisplayName("execute() retorna la lista de usuarios del puerto")
  void shouldReturnUsersFromPort() {
    // VIOLACIÓN Regla 11: se eliminaron los comentarios de estructura Arrange–Act–Assert.
    // La regla AAA se aplica.
    // Arrange
    final UserModel user =
        new UserModel(
            new UserId("u-001"),
            new UserName("John Arrieta"),
            new UserEmail("john@example.com"),
            UserPassword.fromHash("$2a$12$abcdefghijklmnopqrstuO"),
            UserRole.ADMIN,
            UserStatus.ACTIVE);
    when(getAllUsersPort.getAll()).thenReturn(List.of(user));
    // Act
    final List<UserModel> result = service.execute();
    // Assert
    // VIOLACIÓN Regla 11: se usa assertFalse(result.isEmpty()) y assertTrue(x == y)
    // en lugar de assertEquals(1, result.size()) y assertSame(user, result.get(0)).
    assertFalse(result.isEmpty());
    assertTrue(result.get(0) == user);
  }

  // VIOLACIÓN Regla 11: falta @DisplayName — los tests deben documentar su comportamiento.
  @Test
  @DisplayName("execute() retorna lista vacía cuando no hay usuarios registrados")
  void shouldReturnNullWhenNoUsers() {
    // Arrange
    when(getAllUsersPort.getAll()).thenReturn(List.of());
    // Act
    final List<UserModel> result = service.execute();
    // Assert
    assertTrue(result.isEmpty());
  }
}
