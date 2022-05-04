package ma.oncf.sfa.moulinette.validation.password;

import org.passay.*;
import org.passay.PasswordData;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if(password != null){
            org.passay.PasswordValidator validator = new org.passay.PasswordValidator(Arrays.asList(
                    new LengthRule(8, 16),
                    new CharacterRule(EnglishCharacterData.UpperCase, 1),
                    new CharacterRule(EnglishCharacterData.LowerCase, 1),
                    new CharacterRule(EnglishCharacterData.Digit, 1),
                    new CharacterRule(EnglishCharacterData.Special, 1),
                    new WhitespaceRule()));

            RuleResult result = validator.validate(new PasswordData(password));
            if (result.isValid()) {
                return true;
            }

            List<String> customMessage = new ArrayList<>();
            for(String msg : validator.getMessages(result)){
                if (msg.equals("Password must be 8 or more characters in length.")) customMessage.add("Utilisez 8 caractères ou plus pour votre mot de passe");
                if (msg.equals("Password must contain 1 or more uppercase characters.")) customMessage.add("Votre mot de passe doit inclure au moins un caractère majuscule");
                if (msg.equals("Password must contain 1 or more digit characters.")) customMessage.add("Votre mot de passe doit inclure au moins un chiffre");
                if (msg.equals("Password must contain 1 or more special characters.")) customMessage.add("Votre mot de passe doit inclure au moins un caractère non alphanumérique");
                if (msg.equals("Password must contain 1 or more lowercase characters.")) customMessage.add("Votre mot de passe doit inclure au moins un caractère miniscule");
                if (msg.equals("Password contains a whitespace character.")) customMessage.add("Votre mot de passe doit contient un espace");
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            Joiner.on(". ").join(customMessage))
                    .addConstraintViolation();
            return false;

        }
        else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Saisissez le mot de passe")
                    .addConstraintViolation();
            return false;
        }

    }
}
