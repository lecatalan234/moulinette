package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.UtilisateurReqDto;
import ma.oncf.sfa.moulinette.dto.UtilisateurResDto;
import ma.oncf.sfa.moulinette.entities.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    UtilisateurResDto utilisateurToUtilsateurDTO(Utilisateur utilisateur);
    Utilisateur utilisateurDtoToUtilisateur(UtilisateurReqDto utilisateurReqDto);
}
