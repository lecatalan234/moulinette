package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Role;
import javax.validation.constraints.NotBlank;

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
