package ma.oncf.sfa.moulinette.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Utilisateur;

import javax.persistence.*;
import java.util.List;

@Data
public class RoleReqDto {
    private Integer id;
    private String nomRole;
    private List<Utilisateur> utilisateurs;
}
