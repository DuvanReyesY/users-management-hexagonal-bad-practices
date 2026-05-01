package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.UserPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserPersistenceMapperMapStruct {

    UserPersistenceMapperMapStruct INSTANCE = Mappers.getMapper(UserPersistenceMapperMapStruct.class);

    @Mapping(target = "id",       expression = "java(user.getIdValue())")
    @Mapping(target = "name",     expression = "java(user.getNameValue())")
    @Mapping(target = "email",    expression = "java(user.getEmailValue())")
    @Mapping(target = "password", expression = "java(user.verifyPassword())")
    @Mapping(target = "role",     expression = "java(user.getRole().name())")
    @Mapping(target = "status",   expression = "java(user.getStatus().name())")
    @Mapping(target = "createdAt", expression = "java(null)")
    @Mapping(target = "updatedAt", expression = "java(null)")
    UserPersistenceDto fromModelToDto(UserModel user);

    @Mapping(target = "id",       expression = "java(new com.jcaa.usersmanagement.domain.valueobject.UserId(entity.id()))")
    @Mapping(target = "name",     expression = "java(new com.jcaa.usersmanagement.domain.valueobject.UserName(entity.name()))")
    @Mapping(target = "email",    expression = "java(new com.jcaa.usersmanagement.domain.valueobject.UserEmail(entity.email()))")
    @Mapping(target = "password", expression = "java(com.jcaa.usersmanagement.domain.valueobject.UserPassword.fromHash(entity.password()))")
    @Mapping(target = "role",     expression = "java(com.jcaa.usersmanagement.domain.enums.UserRole.fromString(entity.role()))")
    @Mapping(target = "status",   expression = "java(com.jcaa.usersmanagement.domain.enums.UserStatus.fromString(entity.status()))")
    UserModel fromEntityToModel(UserEntity entity);
}