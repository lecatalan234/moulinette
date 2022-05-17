package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.ParametrageComptableReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageComptableResDto;
import ma.oncf.sfa.moulinette.entities.ParametrageComptable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParametrageComptableMapper {
    ParametrageComptable paramComptableDtoToParamComptable(ParametrageComptableReqDto parametrageComptableReqDto);
    ParametrageComptableResDto paramComptableToParamComptableDto(ParametrageComptable parametrageComptable);
}
