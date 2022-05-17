package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Comptabilite;
import ma.oncf.sfa.moulinette.entities.EnrMouvement;
import ma.oncf.sfa.moulinette.entities.Importation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ImportationRepositoryCustomImpl implements ImportationRepositoryCustom<Importation, Integer> {
    private final EntityManager entityManager;

    public ImportationRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<EnrMouvement> selectMouvementCibUpdate(String requeteSql) {
        Query query = entityManager.createNativeQuery(requeteSql,EnrMouvement.class);
        return (List<EnrMouvement>) query.getResultList();
    }

    @Override
    public List<Comptabilite> selectLignesComptaFluxUpdate(String requeteSql) {
        Query query = entityManager.createNativeQuery(requeteSql,Comptabilite.class);
        return (List<Comptabilite>) query.getResultList();
    }
}
