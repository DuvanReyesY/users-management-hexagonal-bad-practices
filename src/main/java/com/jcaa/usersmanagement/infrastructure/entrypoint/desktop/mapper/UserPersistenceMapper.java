package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.UserEntity;

public final class UserPersistenceMapper {

    public UserEntity toEntity(final UserModel userModel) {
        return new UserEntity(
                userModel.getId().value(),
                userModel.getName().value(),
                userModel.getEmail().value(),
                userModel.getPassword().value(),
                userModel.getRole().name(),
                userModel.getStatus().name(),
                null,
                null);
    }

    public UserModel toModel(final UserEntity userEntity) {
        return new UserModel(
               new UserId(userEntity.id()),
               new UserName(userEntity.name()),
               new UserEmail(userEntity.email()),
               UserPassword.fromHash(userEntity.password()),
                UserRole.valueOf(userEntity.role()),
                UserStatus.valueOf(userEntity.status())

        );
    }


}
