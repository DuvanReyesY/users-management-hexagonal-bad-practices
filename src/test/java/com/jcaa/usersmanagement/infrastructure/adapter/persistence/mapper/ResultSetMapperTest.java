package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.UserEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests para ResultSetMapper.
 *
 * <p>Cubre: mapeo de ResultSet a UserEntity, propagación de SQLException,
 * lista vacía cuando el ResultSet no tiene filas, y una fila por registro en el ResultSet.
 */
@DisplayName("ResultSetMapper")
@ExtendWith(MockitoExtension.class)
class ResultSetMapperTest {

    private static final String ID = "u-001";
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john@example.com";
    private static final String HASH = "$2a$12$abcdefghijklmnopqrstuO";
    private static final String ROLE = "ADMIN";
    private static final String STATUS = "ACTIVE";
    private static final String CREATED_AT = "2024-01-01 00:00:00";
    private static final String UPDATED_AT = "2024-01-02 00:00:00";

    @Mock
    private ResultSet resultSet;

    // ========== toEntity() TESTS ==========

    @Test
    @DisplayName("toEntity() reads all eight columns from the ResultSet")
    void shouldReadAllColumnsFromResultSet() throws SQLException {
        // Arrange
        when(resultSet.getString("id")).thenReturn(ID);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("email")).thenReturn(EMAIL);
        when(resultSet.getString("password")).thenReturn(HASH);
        when(resultSet.getString("role")).thenReturn(ROLE);
        when(resultSet.getString("status")).thenReturn(STATUS);
        when(resultSet.getString("created_at")).thenReturn(CREATED_AT);
        when(resultSet.getString("updated_at")).thenReturn(UPDATED_AT);

        // Act
        final UserEntity result = ResultSetMapper.toEntity(resultSet);

        // Assert
        assertAll(
                "toEntity()",
                () -> assertEquals(ID, result.id(), "id"),
                () -> assertEquals(NAME, result.name(), "name"),
                () -> assertEquals(EMAIL, result.email(), "email"),
                () -> assertEquals(HASH, result.password(), "password"),
                () -> assertEquals(ROLE, result.role(), "role"),
                () -> assertEquals(STATUS, result.status(), "status"),
                () -> assertEquals(CREATED_AT, result.createdAt(), "createdAt"),
                () -> assertEquals(UPDATED_AT, result.updatedAt(), "updatedAt"));
    }

    @Test
    @DisplayName("toEntity() propagates SQLException when ResultSet read fails")
    void shouldPropagateExceptionFromResultSet() throws SQLException {
        // Arrange
        when(resultSet.getString(anyString())).thenThrow(new SQLException("Column read failed"));

        // Act & Assert
        assertThrows(
                SQLException.class,
                () -> ResultSetMapper.toEntity(resultSet),
                "must propagate SQLException when ResultSet throws on getString");
    }

    // ========== toModelList() TESTS ==========

    @Test
    @DisplayName("toModelList() returns an empty list when ResultSet has no rows")
    void shouldReturnEmptyListWhenResultSetIsEmpty() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(false);

        // Act
        final List<UserModel> result = ResultSetMapper.toModelList(resultSet);

        // Assert
        assertTrue(result.isEmpty(), "must return an empty list when ResultSet has no rows");
    }

    @Test
    @DisplayName("toModelList() returns one model per row in the ResultSet")
    void shouldReturnOneModelPerRow() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("id")).thenReturn(ID, "u-002");
        when(resultSet.getString("name")).thenReturn(NAME, "Jane Doe");
        when(resultSet.getString("email")).thenReturn(EMAIL, "jane@example.com");
        when(resultSet.getString("password")).thenReturn(HASH, HASH);
        when(resultSet.getString("role")).thenReturn(ROLE, "MEMBER");
        when(resultSet.getString("status")).thenReturn(STATUS, "PENDING");
        when(resultSet.getString("created_at")).thenReturn(CREATED_AT, CREATED_AT);
        when(resultSet.getString("updated_at")).thenReturn(UPDATED_AT, UPDATED_AT);

        // Act
        final List<UserModel> result = ResultSetMapper.toModelList(resultSet);

        // Assert
        assertEquals(2, result.size(), "must return one model per row in the ResultSet");
    }

    @Test
    @DisplayName("toModelList() propagates SQLException when a row read fails")
    void shouldPropagateExceptionDuringIteration() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(anyString())).thenThrow(new SQLException("Row read failed"));

        // Act & Assert
        assertThrows(
                SQLException.class,
                () -> ResultSetMapper.toModelList(resultSet),
                "must propagate SQLException when a row fails to be read");
    }
}