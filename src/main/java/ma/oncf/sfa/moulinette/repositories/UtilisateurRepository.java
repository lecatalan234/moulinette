package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;


public interface  UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findUtilisateurByMatricule(String matricule);
}
