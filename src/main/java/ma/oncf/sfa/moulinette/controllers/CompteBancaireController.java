package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.CompteBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.CompteBancaireResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("ComptesBancaires")
public interface CompteBancaireController {
    @Operation(tags = "ComptesBancaires")
    @GetMapping(path = "/comptesBancaires")
    ResponseEntity<List<CompteBancaireResDto>> getAllComptesBancaires();

    @Operation(tags = "ComptesBancaires")
    @PostMapping(path = "/comptesBancaires")
    ResponseEntity<CompteBancaireResDto> save(@Valid @RequestBody CompteBancaireReqDto compteBancaireReqDto);

    @Operation(tags = "ComptesBancaires")
    @PutMapping(path = "/comptesBancaires/{id}")
    ResponseEntity<CompteBancaireResDto> update(@Valid @RequestBody CompteBancaireReqDto compteBancaireReqDto, @PathVariable Integer id);

    @Operation(tags = "ComptesBancaires")
    @DeleteMapping(path = "comptesBancaires/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Integer id);

    @Operation(tags = "ComptesBancaires")
    @GetMapping(path = "/comptesBancaires/{id}")
    ResponseEntity<CompteBancaireResDto> getCompteBancaireById(@PathVariable Integer id);
}
