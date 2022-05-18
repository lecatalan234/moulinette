package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Importation;

@Data
public class FichierReqDto {
    private Integer id;
    private String nomFichier;
    private Long taille;
    private Importation importation;
}
