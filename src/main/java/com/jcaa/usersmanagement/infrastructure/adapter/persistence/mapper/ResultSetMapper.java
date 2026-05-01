package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ResultSetMapper {

    public UserEntity toEntity(final ResultSet resultSet) throws SQLException {
        return new UserEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("role"),
                resultSet.getString("status"),
                resultSet.getString("created_at"),
                resultSet.getString("updated_at"));
    }

    public UserModel toModel(final ResultSet resultSet) throws SQLException {
        return UserPersistenceMapperMapStruct.INSTANCE.fromEntityToModel(toEntity(resultSet));
    }

    public List<UserModel> toModelList(final ResultSet resultSet) throws SQLException {
        final List<UserModel> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(toModel(resultSet));
        }
        return users;
    }
}