package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.EnrComplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrComplementRepository extends JpaRepository<EnrComplement,Integer> {
    @Query(value = " SELECT * From enr_complement " +
                   " WHERE enr_mvt_id IN (SELECT id from enr_mouvement where enr_ancien_solde_id IN " +
                   " (SELECT id from enr_ancien_solde where fichier_id IN " +
                   " (SELECT id from fichier where importation_id = :idImportation)))",nativeQuery = true)
    List<EnrComplement> getAllComplementsByImportation(Integer idImportation);

    @Query(value = "SELECT enr_mvt_id from enr_complement where id = :idComplement", nativeQuery = true)
    Integer getIdMouvementByComplementId(Integer idComplement);

    @Query(value = "SELECT COUNT(*) FROM enr_complement where enr_mvt_id = :idMouvement",nativeQuery = true)
    Integer getNbrEnrComplementByMouvement(Integer idMouvement);
}
