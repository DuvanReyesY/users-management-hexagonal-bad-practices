package com.jcaa.usersmanagement.domain.exception;

public final class EmailSenderException extends DomainException {

  private static final String NOTIFY_NOT_SEND = "La notificación por correo no pudo ser enviada.";
  private static final String EMAIL_NOT_SEND = "No se pudo enviar el correo a '%s'. Error SMTP: %s";

  private EmailSenderException(final String message) {
    super(message);
  }

  private EmailSenderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static EmailSenderException becauseSmtpFailed(
      final String destinationEmail, final String smtpError) {
    // VIOLACIÓN Regla 10: textos hardcodeado AHORA son constantes.

    return new EmailSenderException(
        String.format(EMAIL_NOT_SEND, destinationEmail, smtpError));
  }

  public static EmailSenderException becauseSendFailed(final Throwable cause) {

    return new EmailSenderException(NOTIFY_NOT_SEND, cause);
  }
}
