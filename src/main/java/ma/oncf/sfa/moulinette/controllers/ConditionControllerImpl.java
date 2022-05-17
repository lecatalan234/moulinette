package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.ConditionReqDto;
import ma.oncf.sfa.moulinette.dto.ConditionResDto;
import ma.oncf.sfa.moulinette.services.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@SecurityRequirement(name = "moulinette-api")
public class ConditionControllerImpl implements ConditionController {
    @Autowired
    private ConditionService conditionService;

    @Override
    public ResponseEntity<List<ConditionResDto>> getAllCondition() {
        return ResponseEntity.status(HttpStatus.OK).body(conditionService.getAllConditions());
    }

    @Override
    public ResponseEntity<ConditionResDto> save(ConditionReqDto conditionReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(conditionService.save(conditionReqDto));
    }

    @Override
    public ResponseEntity<ConditionResDto> update(ConditionReqDto conditionReqDto, Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(conditionService.update(conditionReqDto, id));
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        conditionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ConditionResDto>> getAllConditionByParamBancaire(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(conditionService.getConditionByParamBancaire(id));
    }

    @Override
    public ResponseEntity<List<ConditionResDto>> getAllConditionByParamComptable(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(conditionService.getConditionByParamCompta(id));
    }
}
