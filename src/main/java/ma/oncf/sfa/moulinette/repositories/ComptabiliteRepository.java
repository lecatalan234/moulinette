package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Comptabilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComptabiliteRepository extends JpaRepository<Comptabilite, Integer> {

    @Query(value = " Select * From comptabilite" +
                   " where fichier_id IN (SELECT id from fichier where importation_id = :idImportation) order by id asc",nativeQuery = true)
    List<Comptabilite> getAllLignesComptaByImportation(Integer idImportation);
}
