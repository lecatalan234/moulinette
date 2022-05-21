package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.ParametrageComptable;

import java.util.List;

@Data
public class FluxResDto {
    private Integer id;
    private String codeFlux;
    private String designation;
    private List<ParametrageComptable> parametrageComptables;
    private List<ParametrageComptable> parametrageComptablesNF;
}
