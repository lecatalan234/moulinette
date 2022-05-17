package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.Comptabilite;
import ma.oncf.sfa.moulinette.entities.EnrMouvement;

import java.util.List;

public interface ImportationRepositoryCustom<Importation, Integer>{
    List<EnrMouvement> selectMouvementCibUpdate(String requeteSql);
    List<Comptabilite> selectLignesComptaFluxUpdate(String requeteSql);
}
