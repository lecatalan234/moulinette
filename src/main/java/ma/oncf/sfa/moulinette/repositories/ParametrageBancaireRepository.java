package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Banque;
import ma.oncf.sfa.moulinette.entities.ParametrageBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParametrageBancaireRepository extends JpaRepository<ParametrageBancaire, Integer> {

    @Query(value = "SELECT * FROM parametrage_bancaire p, conditions c" +
                   " WHERE p.id = c.parametrage_bancaire_id order by p.id desc", nativeQuery = true)
    List<ParametrageBancaire> getAllParamBancaireWithCondition();

    List<ParametrageBancaire> findParametrageBancaireByBanque(Banque banque);
}
