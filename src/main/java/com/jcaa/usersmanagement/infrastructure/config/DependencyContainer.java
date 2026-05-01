package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteUserUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllUsersUseCase;
import com.jcaa.usersmanagement.application.port.in.GetUserByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateUserUseCase;
import com.jcaa.usersmanagement.application.service.CreateUserService;
import com.jcaa.usersmanagement.application.service.DeleteUserService;
import com.jcaa.usersmanagement.application.service.EmailNotificationService;
import com.jcaa.usersmanagement.application.service.GetAllUsersService;
import com.jcaa.usersmanagement.application.service.GetUserByIdService;
import com.jcaa.usersmanagement.application.service.LoginService;
import com.jcaa.usersmanagement.application.service.UpdateUserService;
import com.jcaa.usersmanagement.infrastructure.adapter.email.JavaMailEmailSenderAdapter;
import com.jcaa.usersmanagement.infrastructure.adapter.email.SmtpConfig;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.config.DatabaseConfig;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.config.DatabaseConnectionFactory;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository.UserRepositoryMySQL;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import jakarta.validation.Validator;
import java.sql.Connection;

public final class DependencyContainer {

    private static final String DB_HOST      = "db.host";
    private static final String DB_PORT      = "db.port";
    private static final String DB_NAME      = "db.name";
    private static final String DB_USER      = "db.username";
    private static final String DB_PASSWORD  = "db.password";

    private static final String SMTP_HOST      = "smtp.host";
    private static final String SMTP_PORT      = "smtp.port";
    private static final String SMTP_USER      = "smtp.username";
    private static final String SMTP_PASSWORD  = "smtp.password";
    private static final String SMTP_FROM      = "smtp.from.address";
    private static final String SMTP_FROM_NAME = "smtp.from.name";

    private final UserController userController;

    public DependencyContainer() {
        final AppProperties properties       = new AppProperties();
        final UserRepositoryMySQL repository = buildRepository(properties);
        final EmailNotificationService email = buildEmailNotificationService(properties);
        final Validator validator            = ValidatorProvider.buildValidator();

        this.userController = buildUserController(repository, email, validator);
    }

    public UserController userController() {
        return userController;
    }

    private static UserRepositoryMySQL buildRepository(final AppProperties properties) {
        final Connection connection = buildDatabaseConnection(properties);
        return new UserRepositoryMySQL(connection);
    }

    private static EmailNotificationService buildEmailNotificationService(
            final AppProperties properties) {
        final JavaMailEmailSenderAdapter emailSender =
                new JavaMailEmailSenderAdapter(buildSmtpConfig(properties));
        return new EmailNotificationService(emailSender);
    }

    private static UserController buildUserController(
            final UserRepositoryMySQL repository,
            final EmailNotificationService email,
            final Validator validator) {

        final CreateUserUseCase  createUser = new CreateUserService(repository, repository, email, validator);
        final UpdateUserUseCase  updateUser = new UpdateUserService(repository, repository, repository, email, validator);
        final DeleteUserUseCase  deleteUser = new DeleteUserService(repository, repository, validator);
        final GetUserByIdUseCase getById = new GetUserByIdService(repository, validator);
        final GetAllUsersUseCase getAll = new GetAllUsersService(repository);
        final LoginUseCase login = new LoginService(repository, validator);

        return new UserController(createUser, updateUser, deleteUser, getById, getAll, login);
    }

    private static Connection buildDatabaseConnection(final AppProperties properties) {
        final DatabaseConfig config = new DatabaseConfig(
                properties.get(DB_HOST),
                properties.getInt(DB_PORT),
                properties.get(DB_NAME),
                properties.get(DB_USER),
                properties.get(DB_PASSWORD));
        return DatabaseConnectionFactory.createConnection(config);
    }

    private static SmtpConfig buildSmtpConfig(final AppProperties properties) {
        return new SmtpConfig(
                properties.get(SMTP_HOST),
                properties.getInt(SMTP_PORT),
                properties.get(SMTP_USER),
                properties.get(SMTP_PASSWORD),
                properties.get(SMTP_FROM),
                properties.get(SMTP_FROM_NAME));
    }
}