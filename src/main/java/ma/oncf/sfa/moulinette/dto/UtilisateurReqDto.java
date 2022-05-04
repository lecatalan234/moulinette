package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Role;
import ma.oncf.sfa.moulinette.validation.UniqueValidation.UniqueValidation;
import ma.oncf.sfa.moulinette.validation.password.PasswordValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class UtilisateurReqDto {
    private Integer id;

    @NotBlank(message = "Entrez le nom")
    private String nom;

    @NotBlank(message = "Entrez le prénom")
    private String prenom;

    @NotBlank(message = "Entrez le matricule")
    @Pattern(regexp = "\\d\\d\\d\\d\\d[a-zA-Z]", message = "Entrer un matricule valide (44444E)")
    @UniqueValidation(message = "Le matricule est déjà utilisé", fieldName = "matricule")
    private String matricule;

    @PasswordValidation
    private String motDePasse;

    @NotNull(message = "Sélectionnez l'état du compte")
    private Boolean EtatCompte;

    private Role role;
}
