package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.out.EmailSenderPort;
import com.jcaa.usersmanagement.domain.exception.EmailSenderException;
import com.jcaa.usersmanagement.domain.model.EmailDestinationModel;
import com.jcaa.usersmanagement.domain.model.UserModel;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public final class EmailNotificationService {

  private static final String SUBJECT_CREATED = "Tu cuenta ha sido creada — Gestión de Usuarios";
  private static final String SUBJECT_UPDATED =
      "Tu cuenta ha sido actualizada — Gestión de Usuarios";

  private static final String TOKEN_NAME     = "name";
  private static final String TOKEN_EMAIL    = "email";
  private static final String TOKEN_PASSWORD = "password";
  private static final String TOKEN_ROLE     = "role";
  private static final String TOKEN_STATUS   = "status";

  private final EmailSenderPort emailSenderPort;

  public void notifyUserCreated(final UserModel user, final String plainPassword) {

    // Clean Code - Regla 25 (claridad sobre ingenio) y Regla 26 (evitar sobrecompactación) solucionadas
    // Clean Code - Regla 3 (un solo nivel de abstracción por función):
    // Se separo la logica en un segundo metodo de update que ya separa correctamente cada nivel de abstraccion
    // Clean Code - Regla 11 (evitar duplicación): la construcción de tokens del mapa
    // se movio a un metodo aparte y ahora es diferente a los tokens de mapa de update

     String template = loadTemplate("user-created.html");
     String body = renderTemplate(template, buildCreatedTokens(user, plainPassword));
     EmailDestinationModel destination = buildDestination(user, SUBJECT_CREATED, body);
    sendWithWarningOnFailure(destination);

  }
    // Clean Code - Regla 11 (evitar duplicación): se separo la misma estructura que tenia de notifyUserCreated

    public void notifyUserUpdated(final UserModel user) {
      String template = loadTemplate("user-updated.html");
      String body = renderTemplate(template, buildUpdatedTokens(user));
      EmailDestinationModel destination = buildDestination(user, SUBJECT_UPDATED, body);
      sendWithWarningOnFailure(destination);
    }

  private Map<String, String> buildCreatedTokens(final UserModel user, final String plainPassword) {
    return Map.of(
            TOKEN_NAME,     user.getName().value(),
            TOKEN_EMAIL,    user.getEmail().value(),
            TOKEN_PASSWORD, plainPassword,
            TOKEN_ROLE,     user.getRole().name()
    );
  }

  private Map<String, String> buildUpdatedTokens(final UserModel user) {
    return Map.of(
            TOKEN_NAME,   user.getName().value(),
            TOKEN_EMAIL,  user.getEmail().value(),
            TOKEN_ROLE,   user.getRole().name(),
            TOKEN_STATUS, user.getStatus().name()
    );
  }

  // Clean Code - Regla 6 (evitar parámetros booleanos de control): se elimino el metodo que ejectuta cambios booleanos
  // y se dejaron solo los dos metodos create y update que ya estaban

  private static EmailDestinationModel buildDestination(
      final UserModel user, final String subject, final String body) {
    return new EmailDestinationModel(
        user.getEmail().value(), user.getName().value(), subject, body);
  }

  private String loadTemplate(final String templateName) {
    final String path = "/templates/" + templateName;
    try (InputStream inputStream = openResourceStream(path)) {
      if (Objects.isNull(inputStream)) {
        throw EmailSenderException.becauseSendFailed(
            new IllegalStateException("Template not found: " + path));
      }
      return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (final IOException ioException) {
      throw EmailSenderException.becauseSendFailed(ioException);
    }
  }

  InputStream openResourceStream(final String path) {
    return getClass().getResourceAsStream(path);
  }

  // VIOLACIÓN Regla 4: el método privado ya está declarado como static. La regla dice: métodos privados sin estado deben ser static.
  private static String renderTemplate(final String template, final Map<String, String> values) {
    String result = template;
    for (final Map.Entry<String, String> tokenEntry : values.entrySet()) {
      final String token = "{{" + tokenEntry.getKey() + "}}";
      result = result.replace(token, tokenEntry.getValue());
    }
    return result;
  }

  // Clean Code - Regla 7 (evitar efectos secundarios ocultos):
  // El nombre "sendOrLog" prometia dos cosas (enviar o loguear), pero ninguna de las
  // dos describe el comportamiento real completo por eso se le cambio el nombre a sendWithWarningOnFailure para que
  //el llamador ya sepa que esperar sin leer la implementacion

  private void sendWithWarningOnFailure(final EmailDestinationModel destination) {
    try {
      emailSenderPort.send(destination);
    } catch (final EmailSenderException senderException) {
      log.log(
              Level.WARNING,
              "[EmailNotificationService] No se pudo enviar correo a: {0}. Causa: {1}",
              new Object[] {destination.getDestinationEmail(), senderException.getMessage()});
      throw senderException;
    }
  }
}
