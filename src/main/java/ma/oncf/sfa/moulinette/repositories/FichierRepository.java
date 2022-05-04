package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichierRepository extends JpaRepository<Fichier, Integer> {
}
