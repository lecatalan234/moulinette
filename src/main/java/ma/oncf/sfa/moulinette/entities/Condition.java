package ma.oncf.sfa.moulinette.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "conditions")
public class Condition extends Auditable<String>{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String operateurLogique;
    private String champ;
    private String operateur;
    private String valeur;
    private String conditionSQL;
    private Integer classementCondition;

    @ManyToOne
    @JsonIgnore
    private ParametrageBancaire parametrageBancaire;
}
