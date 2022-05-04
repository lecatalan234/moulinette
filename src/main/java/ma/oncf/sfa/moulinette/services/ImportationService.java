package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ImportationReqDto;
import ma.oncf.sfa.moulinette.dto.ImportationResDto;
import ma.oncf.sfa.moulinette.entities.EnrMouvement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImportationService {
    List<String> chargementFichierBancaire(List<MultipartFile> multipartFiles, Integer idBanque, String matricule);
    ImportationResDto save(ImportationReqDto importationReqDto);
    List<ImportationResDto> getAllImportation();
    void deleteById(Integer id);
    List<ImportationResDto> getImportationByUtilisateur(String matricule);
    List<EnrMouvement> enrichirFichierBancaire(Integer idImportation);
    Integer getLastImportationByMatricule(String matricule);
    String telechargerFichierBancaire(Integer idImportation);
}
