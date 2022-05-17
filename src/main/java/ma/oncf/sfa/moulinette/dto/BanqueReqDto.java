package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Importation;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BanqueReqDto {
    private Integer id;
    @NotBlank(message = "Entrez le code de la banque")
    private String codeBanque;
    @NotBlank(message = "Entrez le nom de la banque")
    private String nomBanque;
    private List<Importation> importations;
}
