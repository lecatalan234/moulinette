package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.ParametrageBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageBancaireResDto;
import ma.oncf.sfa.moulinette.entities.ParametrageBancaire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParametrageBancaireMapper {
    ParametrageBancaire paramBancaireDtoToParamBancaire(ParametrageBancaireReqDto parametrageBancaireReqDto);
    ParametrageBancaireResDto paramBancaireToParamBancaireDto(ParametrageBancaire parametrageBancaire);
}
