package ma.oncf.sfa.moulinette.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Importation;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
public class BanqueReqDto {
    private Integer id;
    private String codeBanque;
    private String nomBanque;
    private List<Importation> importations;
}
