package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Integer> {
    CompteBancaire findCompteBancaireByNumeroCompte(String numCompte);
}
