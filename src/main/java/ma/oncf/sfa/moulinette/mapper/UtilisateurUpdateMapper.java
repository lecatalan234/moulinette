package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.UtilisateurUpdateReqDto;
import ma.oncf.sfa.moulinette.dto.UtilisateurUpdateResDto;
import ma.oncf.sfa.moulinette.entities.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurUpdateMapper {
    UtilisateurUpdateResDto utilisateurToUtilsateurUpdateDTO(Utilisateur utilisateur);
    Utilisateur utilisateurUpdateDtoToUtilisateur(UtilisateurUpdateReqDto utilisateurUpdateReqDto);
}
