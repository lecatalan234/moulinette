package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Condition;
import ma.oncf.sfa.moulinette.entities.ParametrageBancaire;
import ma.oncf.sfa.moulinette.entities.ParametrageComptable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {
    List<Condition> findConditionByParametrageBancaire(ParametrageBancaire parametrageBancaire);
    List<Condition> findConditionByParametrageComptable(ParametrageComptable parametrageComptable);
}
