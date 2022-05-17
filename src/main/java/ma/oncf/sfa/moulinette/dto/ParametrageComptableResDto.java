package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Condition;
import ma.oncf.sfa.moulinette.entities.Flux;

import java.util.List;

@Data
public class ParametrageComptableResDto {

    private Integer id;
    private Boolean actif = true;
    private List<Condition> conditions;
    private Flux flux;
    private Flux nouveauFlux;
}
