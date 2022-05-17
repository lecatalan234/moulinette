package ma.oncf.sfa.moulinette.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flux extends Auditable<String>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String flux;
    private String designation;

    @OneToMany(mappedBy = "flux")
    @JsonIgnore
    private List<ParametrageComptable> parametrageComptables;

    @OneToMany(mappedBy = "nouveauFlux")
    @JsonIgnore
    private List<ParametrageComptable> parametrageComptablesNF;
}
