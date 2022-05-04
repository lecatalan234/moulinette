package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Role;

@Data
public class UtilisateurUpdateResDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String matricule;
    private Boolean EtatCompte;
    private Role role;
}
