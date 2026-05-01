package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.UserPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests para UserPersistenceMapperMapStruct.
 *
 * <p>Cubre: mapeo de UserModel a UserPersistenceDto, mapeo de UserEntity a UserModel.
 */
@DisplayName("UserPersistenceMapper")
class UserPersistenceMapperTest {

  private static final String ID = "u-001";
  private static final String NAME = "John Doe";
  private static final String EMAIL = "john@example.com";
  private static final String HASH = "$2a$12$abcdefghijklmnopqrstuO";
  private static final String ROLE = "ADMIN";
  private static final String STATUS = "ACTIVE";
  private static final String CREATED_AT = "2024-01-01 00:00:00";
  private static final String UPDATED_AT = "2024-01-02 00:00:00";

  private UserModel userModel;
  private UserEntity userEntity;

  @BeforeEach
  void setUp() {
    userModel = new UserModel(
            new UserId(ID),
            new UserName(NAME),
            new UserEmail(EMAIL),
            UserPassword.fromHash(HASH),
            UserRole.ADMIN,
            UserStatus.ACTIVE);

    userEntity = new UserEntity(ID, NAME, EMAIL, HASH, ROLE, STATUS, CREATED_AT, UPDATED_AT);
  }

  // ========== fromModelToDto() TESTS ==========

  @Test
  @DisplayName("fromModelToDto() maps all UserModel fields and sets null timestamps")
  void shouldMapModelToDto() {
    // Act
    final UserPersistenceDto result = UserPersistenceMapperMapStruct.INSTANCE.fromModelToDto(userModel);

    // Assert
    assertAll(
            "fromModelToDto()",
            () -> assertEquals(ID, result.id(), "id"),
            () -> assertEquals(NAME, result.name(), "name"),
            () -> assertEquals(EMAIL, result.email(), "email"),
            () -> assertEquals(HASH, result.password(), "password"),
            () -> assertEquals(ROLE, result.role(), "role"),
            () -> assertEquals(STATUS, result.status(), "status"),
            () -> assertNull(result.createdAt(), "createdAt must be null"),
            () -> assertNull(result.updatedAt(), "updatedAt must be null"));
  }

  // ========== fromEntityToModel() TESTS ==========

  @Test
  @DisplayName("fromEntityToModel() maps all UserEntity fields to a domain UserModel")
  void shouldMapEntityToModel() {
    // Act
    final UserModel result = UserPersistenceMapperMapStruct.INSTANCE.fromEntityToModel(userEntity);

    // Assert
    assertAll(
            "fromEntityToModel()",
            () -> assertEquals(ID, result.getId().value(), "id"),
            () -> assertEquals(NAME, result.getName().value(), "name"),
            () -> assertEquals(EMAIL, result.getEmail().value(), "email"),
            () -> assertEquals(UserRole.ADMIN, result.getRole(), "role"),
            () -> assertEquals(UserStatus.ACTIVE, result.getStatus(), "status"));
  }

  // ========== ROUND TRIP TEST ==========

  @Test
  @DisplayName("Round trip: Model -> Dto -> Entity -> Model preserves data")
  void shouldRoundTripSuccessfully() {
    // Act - Model to Dto
    final UserPersistenceDto dto = UserPersistenceMapperMapStruct.INSTANCE.fromModelToDto(userModel);

    // Act - Dto to Entity
    UserEntity entity = new UserEntity(
            dto.id(), dto.name(), dto.email(), dto.password(),
            dto.role(), dto.status(), null, null);

    // Act - Entity to Model
    final UserModel result = UserPersistenceMapperMapStruct.INSTANCE.fromEntityToModel(entity);

    // Assert
    assertAll(
            "Round trip",
            () -> assertEquals(userModel.getId().value(), result.getId().value(), "id"),
            () -> assertEquals(userModel.getName().value(), result.getName().value(), "name"),
            () -> assertEquals(userModel.getEmail().value(), result.getEmail().value(), "email"),
            () -> assertEquals(userModel.getRole(), result.getRole(), "role"),
            () -> assertEquals(userModel.getStatus(), result.getStatus(), "status"));
  }
}