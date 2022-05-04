package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Banque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanqueRepository extends JpaRepository<Banque, Integer> {
}
