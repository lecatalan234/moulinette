package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.FichierReqDto;
import ma.oncf.sfa.moulinette.dto.FichierResDto;

public interface FichierService {
    FichierResDto save(FichierReqDto fichierReqDto);
}
