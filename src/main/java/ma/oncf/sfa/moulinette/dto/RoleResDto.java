package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Utilisateur;

import java.util.List;

@Data
public class RoleResDto {
    private Integer id;
    private String nomRole;
    private List<Utilisateur> utilisateurs;
}
