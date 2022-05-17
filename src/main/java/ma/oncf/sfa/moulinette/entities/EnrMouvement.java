package ma.oncf.sfa.moulinette.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class EnrMouvement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String codeEnregistrement;
    private String codeBanque;
    private String coi;
    private String codeGuichet;
    private String codeDevise;
    private String nbrDecimalesMontant;
    private String zoneReservee1;
    private String NumeroCompte;
    private String cib;
    private String dateOperation;
    private String codeMotifRejet;
    private String dateValeur;
    private String libelle;
    private String zoneReservee2;
    private String NumEcriture;
    private String indiceExoCommession;
    private String indiceIndisponibilite;
    private String montant;
    private String zoneReference;
    private String sens;
    private String nouveauCib;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private EnrAncienSolde EnrAncienSolde;

    @OneToMany(mappedBy = "enrMvt", cascade = CascadeType.ALL)
    @ToString.Exclude
    @OrderBy("id")
    private List<EnrComplement> enregistrementComplements;
}
