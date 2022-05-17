package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.ParametrageComptable;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class FluxReqDto {
    private Integer id;
    @NotBlank(message = "Entrez le flux comptable")
    private String flux;
    @NotBlank(message = "Entrez la désignation")
    private String designation;
    private List<ParametrageComptable> parametrageComptables;
    private List<ParametrageComptable> parametrageComptablesNF;
}
