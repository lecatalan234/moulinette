package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.ParametrageBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageBancaireResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("ParametrageBancaire")
public interface ParametrageBancaireController {
    @Operation(tags = "ParametrageBancaire")
    @GetMapping(path = "/parametrageBancaire")
    ResponseEntity<List<ParametrageBancaireResDto>> getAllParamBancaire();

    @Operation(tags = "ParametrageBancaire")
    @PostMapping(path = "/parametrageBancaire")
    ResponseEntity<ParametrageBancaireResDto> save(@Valid @RequestBody ParametrageBancaireReqDto parametrageBancaireReqDto);

    @Operation(tags = "ParametrageBancaire")
    @PutMapping(path = "/parametrageBancaire/{id}")
    ResponseEntity<ParametrageBancaireResDto> update(@Valid @RequestBody ParametrageBancaireReqDto parametrageBancaireReqDto, @PathVariable Integer id);

    @Operation(tags = "ParametrageBancaire")
    @DeleteMapping(path = "/parametrageBancaire/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Integer id);

    @Operation(tags = "ParametrageBancaire")
    @GetMapping(path = "/parametrageBancaireCondtion")
    ResponseEntity<List<ParametrageBancaireResDto>> getAllParamBancaireWithCondition();

    @Operation()
    @PutMapping(path="/activerParamBancaire/{id}")
    ResponseEntity<ParametrageBancaireResDto> changerEtatParamBancaire(@PathVariable Integer id);
}
