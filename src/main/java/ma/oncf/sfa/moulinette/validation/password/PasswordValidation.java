package ma.oncf.sfa.moulinette.validation.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValidation {
    String message() default "Mot de passe invalide";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
