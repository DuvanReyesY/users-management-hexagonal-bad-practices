package com.jcaa.usersmanagement.infrastructure.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.experimental.UtilityClass;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

<<<<<<< HEAD
// VIOLACIÓN Regla 4: se usa en la clase @UtilityClass para evitar instanciación accidental y generar el constructor privado automáticamente

=======
// VIOLACIÓN se agrego @UtilityClass para evitar instanciación accidental
>>>>>>> 809658b0943448bfb061db978dcbe5473cbc5d98
@UtilityClass
public final class ValidatorProvider {


  public static Validator buildValidator() {
    try (final ValidatorFactory factory = Validation.byDefaultProvider()
        .configure()
        .messageInterpolator(new ParameterMessageInterpolator())
        .buildValidatorFactory()) {
      return factory.getValidator();
    }
  }
}
