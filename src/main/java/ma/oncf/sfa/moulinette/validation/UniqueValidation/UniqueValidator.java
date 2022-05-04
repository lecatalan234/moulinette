package ma.oncf.sfa.moulinette.validation.UniqueValidation;

import ma.oncf.sfa.moulinette.repositories.UtilisateurRepository;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<UniqueValidation, String> {

    private UtilisateurRepository utilisateurRepository;

    private String fieldName;

    public UniqueValidator(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public void initialize(UniqueValidation constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(fieldName.equals("matricule")){
            utilisateurRepository.findUtilisateurByMatricule(value);
            if(utilisateurRepository.findUtilisateurByMatricule(value) == null){
                return true;
            }
            else return false;
        }
        else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("You should specify the value of <fieldName>")
                    .addConstraintViolation();
            return false;
        }

    }
}
