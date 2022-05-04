package ma.oncf.sfa.moulinette.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Importation extends Auditable<String>{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String outFile;

    //B: Bancaire et C: Comptable
    private String typeImportation;

    @ManyToOne
    @ToString.Exclude
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "importation", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Fichier> fichiers;

    @ManyToOne
    @ToString.Exclude
    private Banque banque;
}
