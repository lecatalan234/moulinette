package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.CompteBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.CompteBancaireResDto;
import ma.oncf.sfa.moulinette.entities.CompteBancaire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteBancaireMapper {
    CompteBancaire compteBancaireDtoToCompteBancaire(CompteBancaireReqDto compteBancaireReqDto);
    CompteBancaireResDto compteBancaireToCompteBancaireDto(CompteBancaire compteBancaire);
}
