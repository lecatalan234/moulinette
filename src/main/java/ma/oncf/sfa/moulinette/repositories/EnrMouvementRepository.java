package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.EnrMouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrMouvementRepository extends JpaRepository<EnrMouvement,Integer> {

    @Query(value = " SELECT * From enr_mouvement " +
                   " WHERE enr_ancien_solde_id IN (SELECT id from enr_ancien_solde where fichier_id IN " +
                   " (SELECT id from fichier where importation_id = :idImportation))",nativeQuery = true)
    List<EnrMouvement> getAllMouvementsByImportation(Integer idImportation);

}
