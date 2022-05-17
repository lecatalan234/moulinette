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
public class Comptabilite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String compteBancaire;
    private String codeFlux;
    private String codeBudgetaire;
    private String dateComptable;
    private String dateValeur;
    private String montant;
    private String code;
    private String reference;
    private String libelle;
    private String compteComptable;
    private String imputation;
    private String libelleComplementaire;
    private String codeAnnulation;
    private String numeroPiece;
    private String devise;
    private String sens;
    private String nouveauFlux;

    @ManyToOne()
    @ToString.Exclude
    private Fichier fichier;
}
