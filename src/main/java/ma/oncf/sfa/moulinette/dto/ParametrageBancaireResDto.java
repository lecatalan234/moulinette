package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Banque;
import ma.oncf.sfa.moulinette.entities.Condition;

import java.util.List;

@Data
public class ParametrageBancaireResDto {
    private Integer id;
    private String cib;
    private String nouveauCib;
    private Banque banque;
    private Boolean actif;
    private List<Condition> conditions;
}
