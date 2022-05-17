package ma.oncf.sfa.moulinette.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Fichier extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomFichier;

    private Long taille;

    @ManyToOne()
    private Importation importation;

    @OneToMany(mappedBy = "fichier")
    @JsonIgnore
    private List<EnrAncienSolde> enrAncienSolde;

    @OneToMany(mappedBy = "fichier")
    @JsonIgnore
    private List<Comptabilite> comptabilites;
}
