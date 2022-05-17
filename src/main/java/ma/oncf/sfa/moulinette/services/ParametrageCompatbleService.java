package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ParametrageComptableReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageComptableResDto;

import java.util.List;

public interface ParametrageCompatbleService {
    List<ParametrageComptableResDto> getAllParamComptable();
    ParametrageComptableResDto save(ParametrageComptableReqDto parametrageComptableReqDto);
    ParametrageComptableResDto update(ParametrageComptableReqDto parametrageComptableReqDto, Integer id);
    void deleteById(Integer id);
    List<ParametrageComptableResDto> getAllParamComptableWithCondition();
    ParametrageComptableResDto changerEtatParamComptable(Integer id);
}
