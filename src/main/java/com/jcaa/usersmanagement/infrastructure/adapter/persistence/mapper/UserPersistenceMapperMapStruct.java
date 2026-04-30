/*
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.UserPersistenceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserPersistenceMapperMapStruct {

    UserPersistenceMapperMapStruct INSTANCE = Mappers.getMapper(UserPersistenceMapperMapStruct.class);

    */
/**
     * Convierte UserModel a UserPersistenceDto
     *//*

    @Mapping(target = "id", expression = "java(user.getId().value())")
    @Mapping(target = "name", expression = "java(user.getName().value())")
    @Mapping(target = "email", expression = "java(user.getEmail().value())")
    @Mapping(target = "password", expression = "java(user.getPassword().value())")
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Mapping(target = "status", expression = "java(user.getStatus().name())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserPersistenceDto toDto(UserModel user);

    */
/**
     * Convierte UserPersistenceDto a UserModel
     *//*

    @Mapping(target = "id", expression = "java(new UserId(dto.id()))")
    @Mapping(target = "name", expression = "java(new UserName(dto.name()))")
    @Mapping(target = "email", expression = "java(new UserEmail(dto.email()))")
    @Mapping(target = "password", expression = "java(UserPassword.fromHash(dto.password()))")
    @Mapping(target = "role", expression = "java(UserRole.fromString(dto.role()))")
    @Mapping(target = "status", expression = "java(UserStatus.fromString(dto.status()))")
    UserModel toModel(UserPersistenceDto dto);

    */
/**
     * Convierte ResultSet a UserModel
     *//*

    default UserModel fromResultSetToModel(ResultSet rs) throws SQLException {
        if (rs == null) return null;

        return new UserModel(
                new UserId(rs.getString("id")),
                new UserName(rs.getString("name")),
                new UserEmail(rs.getString("email")),
                UserPassword.fromHash(rs.getString("password")),  // ✅ Usa fromHash
                UserRole.fromString(rs.getString("role")),
                UserStatus.fromString(rs.getString("status"))
        );
    }

    */
/**
     * Convierte ResultSet a lista de UserModel
     *//*

    default List<UserModel> fromResultSetToModelList(ResultSet rs) throws SQLException {
        if (rs == null) return new ArrayList<>();

        List<UserModel> users = new ArrayList<>();
        while (rs.next()) {
            users.add(fromResultSetToModel(rs));
        }
        return users;
    }
}*/
