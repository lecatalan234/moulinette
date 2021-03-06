package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.EnrAncienSolde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrAncienSoldeRepository extends JpaRepository<EnrAncienSolde, Integer> {
    @Query(value = "SELECT *" +
                   " From enr_ancien_solde " +
                   " where fichier_id IN (SELECT id from fichier where importation_id = :idImportation)",nativeQuery = true)
    List<EnrAncienSolde> getAllLignesReleveByImporation(Integer idImportation);

    @Query(value = " SELECT * FROM enr_ancien_solde " +
                   " WHERE fichier_id IN (SELECT id from fichier where created_by= :matricule)", nativeQuery = true)
    List<EnrAncienSolde> getAllLignesReleveByImporationUser(String matricule);

}
