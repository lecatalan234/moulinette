package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Auditable;
import ma.oncf.sfa.moulinette.entities.Banque;

@Data
public class CompteBancaireResDto extends Auditable<String> {
    private Integer id;
    private String numeroCompte;
    private String codeGare;
    private boolean isCentralisateur;
    private Banque banque;
}
