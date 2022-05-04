package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Banque;

import javax.validation.constraints.NotBlank;

@Data
public class CompteBancaireReqDto {
    private Integer id;

    @NotBlank(message = "Entrez le numéro de compte")
    private String numeroCompte;

    private String codeGare;
    private boolean isCentralisateur;
    private Banque banque;
}
