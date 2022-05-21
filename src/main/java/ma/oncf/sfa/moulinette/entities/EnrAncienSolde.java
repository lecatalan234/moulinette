package ma.oncf.sfa.moulinette.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class EnrAncienSolde {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String codeEnregistrement;
    private String codeBanque;
    private String zoneReservee1;
    private String codeGuichet;
    private String codeDevise;
    private String nbrDecimalesMontant;
    private String zoneReservee2;
    private String numeroCompte;
    private String zoneReservee3;
    private String dateSolde;
    private String zoneReservee4;
    private String montant;
    private String zoneReservee5;

    @OneToMany(mappedBy = "enrAncienSolde", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<EnrMouvement> enregistrementMouvements;

    @OneToOne(mappedBy = "enrAncienSolde", cascade = CascadeType.ALL)
    @ToString.Exclude
    private EnrNouveauSolde enregistrementNouveauSolde;

    @ManyToOne()
    @ToString.Exclude
    private Fichier fichier;

}
