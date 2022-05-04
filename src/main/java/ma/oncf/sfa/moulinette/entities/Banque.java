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
public class Banque extends Auditable<String>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codeBanque;

    private String nomBanque;

    @OneToMany(mappedBy = "banque")
    @JsonIgnore
    private List<Importation> importations;

    @OneToMany(mappedBy = "banque")
    @JsonIgnore
    private List<ParametrageBancaire> parametrageBancaires;

    @OneToMany(mappedBy = "banque")
    @JsonIgnore
    private List<CompteBancaire> compteBancaires;
}
