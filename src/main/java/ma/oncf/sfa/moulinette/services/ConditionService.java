package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ConditionReqDto;
import ma.oncf.sfa.moulinette.dto.ConditionResDto;
import ma.oncf.sfa.moulinette.dto.ParametrageBancaireReqDto;

import java.util.List;

public interface ConditionService {
    List<ConditionResDto> getAllConditions();
    ConditionResDto save(ConditionReqDto conditionReqDto);
    ConditionResDto update(ConditionReqDto conditionReqDto, Integer id);
    void deleteById(Integer id);
    List<ConditionResDto> getConditionByParamBancaire(Integer idParam);
}
