package ma.oncf.sfa.moulinette.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class EnrNouveauSolde {
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

    @OneToOne
    @JsonIgnore
    EnrAncienSolde enrAncienSolde;
}
