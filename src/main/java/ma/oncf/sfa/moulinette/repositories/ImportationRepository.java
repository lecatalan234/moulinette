package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Importation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImportationRepository extends JpaRepository<Importation, Integer>, ImportationRepositoryCustom {
    List<Importation> findImportationByCreatedBy(String matricule);

    @Query(value = "SELECT MAX(id) FROM importation where created_by = :matricule",nativeQuery = true)
    Integer getLastImportationByCreatedBy(String matricule);

}
