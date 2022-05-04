package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Role;
import ma.oncf.sfa.moulinette.validation.password.PasswordValidation;

@Data
public class ChangerMotDePasseDto {
    private Integer id;

    @PasswordValidation
    private String motDePasse;

    private String confirmMotDePasse;
}
