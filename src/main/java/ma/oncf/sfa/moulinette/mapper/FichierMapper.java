package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.FichierReqDto;
import ma.oncf.sfa.moulinette.dto.FichierResDto;
import ma.oncf.sfa.moulinette.entities.Fichier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FichierMapper {
    Fichier fichierdtoToFichier(FichierReqDto fichierReqDto);
    FichierResDto fichierToFichierDto(Fichier fichier);
}
