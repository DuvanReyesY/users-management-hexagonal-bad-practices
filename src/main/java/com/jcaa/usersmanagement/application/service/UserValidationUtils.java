package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.service.dto.UserAccessPolicy;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.enums.UserRole;

/**
 * Clean Code - Regla 13 (evitar clases utilitarias innecesarias):
 * Esta clase "Utils" agrupa métodos que en realidad pertenecen a sus respectivos objetos
 * de dominio (UserModel, UserRole, UserStatus) o a los servicios que los usan.
 *
 * La regla dice: no crear clases Utils/Helper/Manager sin una razón sólida.
 * La lógica de negocio vive en los objetos de negocio, no en utilitarios genéricos.
 * Una clase llamada "UserValidationUtils" es señal de:
 *   - diseño pobre
 *   - lógica mal ubicada
 *   - falta de encapsulación en dominio o servicios
 *
 * Clean Code - Regla 23 (minimizar conocimiento disperso):
 * Las reglas de validación de usuario están fragmentadas aquí en vez de estar
 * centralizadas en el propio UserModel o en un servicio de dominio dedicado.
 *
 * Clean Code - Regla 12 (alta cohesión real):
 * Esta clase mezcla responsabilidades que no pertenecen al mismo concepto:
 *   - Validación de estado (isUserActive)
 *   - Validación de rol (isAdmin)
 *   - Validación de formato de email (isValidEmail)
 *   - Validación de contraseña (isValidPassword)
 *   - Verificación de permisos con parámetros mixtos (canPerformAction)
 * Sus métodos no trabajan sobre un mismo concepto o responsabilidad — son un
 * "contenedor de cosas relacionadas vagamente". Eso es exactamente baja cohesión.
 */
public class UserValidationUtils {

  // Clean Code - Regla 13: la validación de si un usuario puede hacer login
  // debería vivir en UserModel.isAllowedToLogin() o en un servicio de dominio.
  public static boolean isUserActive(final UserModel user) {
    return user.getStatus() == UserStatus.ACTIVE;
  }

  // Clean Code - Regla 13: esta regla de negocio (qué roles son administradores)
  // debería encapsularse en UserRole o en un servicio de autorización, no aquí.
  public static boolean isAdmin(final UserModel user) {
    return user.getRole() == UserRole.ADMIN;
  }

  // Clean Code - Regla 11 (evitar duplicación): se elimino metodo duplicado
  // Clean Code - Regla 23: el conocimiento de qué es un email válido está disperso
  // entre UserEmail, UserValidationUtils y potencialmente otras clases.

  // Clean Code - Regla 13: validación que pertenece al value object UserPassword.
  // Clean Code - Regla 18 (magic numbers): el número 8 es un magic number aquí —
  // ya tiene significado en UserPassword pero se repite sin constante.
  public static boolean isValidPassword(final String password) {
    return password != null && password.length() >= 8;
  }

  // Clean Code - Regla 20 (objeto antes que primitivo cuando el concepto lo merezca):
  // ya no son String desnudos sino UserId y UserEmail, que traen sus propias validaciones de dominio encapsuladas.

  // Clean Code - Regla 5 (pocos parámetros): se hace encapsulamiento en el metodo
  // con un objeto de política de acceso.

  public static boolean canPerformAction(final UserAccessPolicy policy) {
    // Clean Code - Regla 17: se simplifica la condición larga y difícil del comienzo
    if (policy.userId() == null || policy.email() == null) {
      return false;
    }
    // Regla 18 — desaparecen los literales "ACTIVE" y "PENDING" porque ahora se compara con UserStatus.ACTIVE y UserStatus.PENDING directamente.
    return (policy.status() == UserStatus.ACTIVE || policy.status() == UserStatus.PENDING)
            && policy.maxInactivityDays() >= 0;
  }
}


