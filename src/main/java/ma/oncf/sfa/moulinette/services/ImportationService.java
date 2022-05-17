package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ImportationReqDto;
import ma.oncf.sfa.moulinette.dto.ImportationResDto;
import ma.oncf.sfa.moulinette.entities.Comptabilite;
import ma.oncf.sfa.moulinette.entities.EnrMouvement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImportationService {
    List<String> chargementFichierBancaire(List<MultipartFile> multipartFiles, Integer idBanque, String matricule);
    List<String> chargementFichierComptable(List<MultipartFile> multipartFiles, String matricule);
    List<EnrMouvement> enrichirFichierBancaire(Integer idImportation);
    List<Comptabilite> enrichirFichierComptable(Integer idImportation);
    String telechargerFichier(Integer idImportation);
    ImportationResDto save(ImportationReqDto importationReqDto);
    List<ImportationResDto> getAllImportation();
    void deleteById(Integer id);
    List<ImportationResDto> getImportationByUtilisateur(String matricule);
    Integer getLastImportationByMatricule(String matricule);
}
