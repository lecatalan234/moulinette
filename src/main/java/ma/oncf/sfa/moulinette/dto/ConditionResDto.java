package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.ParametrageBancaire;

@Data
public class ConditionResDto {
    private Integer id;
    private String operateurLogique;
    private String champ;
    private String operateur;
    private String valeur;
    private Integer classementCondition;
    private ParametrageBancaire parametrageBancaire;
}
