package ma.oncf.sfa.moulinette.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteBancaire extends Auditable<String>{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numeroCompte;
    private String codeGare;
    private boolean isCentralisateur;

    @ManyToOne
    @ToString.Exclude
    private Banque banque;

}
