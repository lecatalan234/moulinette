package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.ParametrageBancaire;

import javax.validation.constraints.NotBlank;

@Data
public class ConditionReqDto {
    private Integer id;

    private String operateurLogique;

    @NotBlank(message = "Choisir le champ")
    private String champ;

    @NotBlank(message = "Choisir l'opérateur")
    private String operateur;

    @NotBlank(message = "Entrer la valeur")
    private String valeur;

    private Integer classementCondition;
    private ParametrageBancaire parametrageBancaire;
}
