package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Auditable;
import ma.oncf.sfa.moulinette.entities.Banque;
import ma.oncf.sfa.moulinette.entities.Fichier;
import ma.oncf.sfa.moulinette.entities.Utilisateur;

import java.util.List;

@Data
public class ImportationResDto extends Auditable<String> {
    private Integer id;
    private String outFile;
    private String typeImportation;
    private Utilisateur utilisateur;
    private List<Fichier> fichiers;
    private Banque banque;
}
