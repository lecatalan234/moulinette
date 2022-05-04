package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Role;
import ma.oncf.sfa.moulinette.validation.UniqueValidation.UniqueValidation;
import ma.oncf.sfa.moulinette.validation.password.PasswordValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UtilisateurUpdateReqDto {
    private Integer id;

    @NotBlank(message = "Entrez le nom")
    private String nom;

    @NotBlank(message = "Entrez le prénom")
    private String prenom;

    private String matricule;

    private Boolean EtatCompte;

    private Role role;
}
