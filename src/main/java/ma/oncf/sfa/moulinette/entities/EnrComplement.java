package ma.oncf.sfa.moulinette.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class EnrComplement {
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
    private String zoneReservee2;
    private String qualifiantZone;
    private String infoComplementaire;
    private String zoneReservee3;

    @ManyToOne
    @JsonIgnore
    EnrMouvement enrMvt;
}
