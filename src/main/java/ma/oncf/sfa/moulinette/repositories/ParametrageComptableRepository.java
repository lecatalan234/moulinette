package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.ParametrageComptable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParametrageComptableRepository extends JpaRepository<ParametrageComptable, Integer> {

    @Query(value = "SELECT * FROM parametrage_comptable p, conditions c" +
                   " WHERE p.id = c.parametrage_comptable_id order by p.id desc", nativeQuery = true)
    List<ParametrageComptable> getAllParamBancaireWithCondition();

}
