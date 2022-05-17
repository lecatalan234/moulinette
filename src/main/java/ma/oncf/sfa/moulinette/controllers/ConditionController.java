package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.ConditionReqDto;
import ma.oncf.sfa.moulinette.dto.ConditionResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("Conditions")
public interface ConditionController {

    @Operation(tags = "Conditions")
    @GetMapping(path = "/conditions")
    ResponseEntity<List<ConditionResDto>> getAllCondition();

    @Operation(tags = "Conditions")
    @PostMapping(path = "/conditions")
    ResponseEntity<ConditionResDto> save(@Valid @RequestBody ConditionReqDto conditionReqDto);

    @Operation(tags = "Conditions")
    @PutMapping(path = "/conditions/{id}")
    ResponseEntity<ConditionResDto> update(@Valid @RequestBody ConditionReqDto conditionReqDto, @PathVariable Integer id);

    @Operation(tags = "Conditions")
    @DeleteMapping(path = "/conditions/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Integer id);

    @Operation(tags = "Conditions")
    @GetMapping(path = "/conditions/ParamBancaire/{id}")
    ResponseEntity<List<ConditionResDto>> getAllConditionByParamBancaire(@PathVariable Integer id);

    @Operation(tags = "Conditions")
    @GetMapping(path = "/conditions/ParamComptable/{id}")
    ResponseEntity<List<ConditionResDto>> getAllConditionByParamComptable(@PathVariable Integer id);
}
