package ma.oncf.sfa.moulinette.repositories;

import ma.oncf.sfa.moulinette.entities.EnrMouvement;

import java.util.List;

public interface ImportationRepositoryCustom<Importation, Integer>{
    List<EnrMouvement> selectMouvementCibUpdate(String requeteSql);
}
