package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.ParametrageComptableReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageComptableResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("ParametrageComptable")
public interface ParametrageComptableController {
    @Operation(tags = "ParametrageComptable")
    @GetMapping(path = "/parametrageComptable")
    ResponseEntity<List<ParametrageComptableResDto>> getAllParamComptable();

    @Operation(tags = "ParametrageComptable")
    @PostMapping(path = "/parametrageComptable")
    ResponseEntity<ParametrageComptableResDto> save(@Valid @RequestBody ParametrageComptableReqDto parametrageComptableReqDto);

    @Operation(tags = "ParametrageComptable")
    @PutMapping(path = "/parametrageComptable/{id}")
    ResponseEntity<ParametrageComptableResDto> update(@Valid @RequestBody ParametrageComptableReqDto parametrageComptableReqDto, @PathVariable Integer id);

    @Operation(tags = "ParametrageComptable")
    @DeleteMapping(path = "/parametrageComptable/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Integer id);

    @Operation(tags = "ParametrageComptable")
    @GetMapping(path = "/parametrageComptableCondtion")
    ResponseEntity<List<ParametrageComptableResDto>> getAllParamComptableWithCondition();

    @Operation()
    @PutMapping(path="/activerParamComptable/{id}")
    ResponseEntity<ParametrageComptableResDto> changerEtatParamComptable(@PathVariable Integer id);
}
