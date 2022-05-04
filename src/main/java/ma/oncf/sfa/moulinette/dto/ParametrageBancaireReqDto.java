package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Banque;
import ma.oncf.sfa.moulinette.entities.Condition;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ParametrageBancaireReqDto {
    private Integer id;

    @NotBlank(message = "Entrer le CIB à modifier")
    private String cib;

    @NotBlank(message = "Entrer le nouveau CIB")
    private String nouveauCib;
    private Banque banque;
    private List<Condition> conditions;
}
