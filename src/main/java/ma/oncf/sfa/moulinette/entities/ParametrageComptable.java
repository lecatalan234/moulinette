package ma.oncf.sfa.moulinette.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ParametrageComptable extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "boolean")
    private Boolean actif = true;

    @OneToMany(mappedBy = "parametrageComptable", cascade = CascadeType.ALL)
    @OrderBy("classementCondition")
    @ToString.Exclude
    private List<Condition> conditions;

    @ManyToOne()
    private Flux flux;

    @ManyToOne()
    private Flux nouveauFlux;

}
