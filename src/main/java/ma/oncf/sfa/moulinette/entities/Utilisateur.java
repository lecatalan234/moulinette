package ma.oncf.sfa.moulinette.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Utilisateur extends Auditable<String> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String matricule;
    private String nom;
    private String prenom;
    private String motDePasse;

    @Column(columnDefinition = "boolean")
    private Boolean EtatCompte;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Importation> importations;
}
