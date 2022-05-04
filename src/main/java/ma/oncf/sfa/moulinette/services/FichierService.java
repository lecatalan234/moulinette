package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.FichierReqDto;
import ma.oncf.sfa.moulinette.dto.FichierResDto;

import java.util.List;

public interface FichierService {
    FichierResDto save(FichierReqDto fichierReqDto);
    FichierResDto update(FichierReqDto fichierReqDto);
    List<FichierResDto> getAllFichiers();
    void deleteById(Integer id);
}
