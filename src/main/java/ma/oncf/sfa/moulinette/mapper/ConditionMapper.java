package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.ConditionReqDto;
import ma.oncf.sfa.moulinette.dto.ConditionResDto;
import ma.oncf.sfa.moulinette.entities.Condition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConditionMapper {
    Condition conditionDtoToCondition(ConditionReqDto conditionReqDto);
    ConditionResDto conditionToConditionDto(Condition condition);
}
