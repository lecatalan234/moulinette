package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Comptabilite;
import ma.oncf.sfa.moulinette.entities.EnrMouvement;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ImportationRepositoryCustomImpl implements ImportationRepositoryCustom{
    private final EntityManager entityManager;

    public ImportationRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<EnrMouvement> selectMouvementCibUpdate(String requeteSql) {
        Query query = entityManager.createNativeQuery(requeteSql,EnrMouvement.class);
        return query.getResultList();
    }

    @Override
    public List<Comptabilite> selectLignesComptaFluxUpdate(String requeteSql) {
        Query query = entityManager.createNativeQuery(requeteSql,Comptabilite.class);
        return query.getResultList();
    }
}
