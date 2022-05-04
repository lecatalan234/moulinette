package ma.oncf.sfa.moulinette.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class ParametrageBancaire extends Auditable<String>{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cib;

    private String nouveauCib;

    @Column(columnDefinition = "boolean")
    private Boolean actif = true;

    @ManyToOne
    @ToString.Exclude
    private Banque banque;

    @OneToMany(mappedBy = "parametrageBancaire", cascade = CascadeType.ALL)
    @OrderBy("classementCondition")
    @ToString.Exclude
    private List<Condition> conditions;

}
