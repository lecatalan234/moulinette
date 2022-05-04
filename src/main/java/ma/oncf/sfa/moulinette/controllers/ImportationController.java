package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.ImportationResDto;
import ma.oncf.sfa.moulinette.entities.EnrMouvement;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api("Importations")
public interface ImportationController {

    @Operation(tags = "Importations")
    @PostMapping(path = "/upload", consumes = {"multipart/form-data"})
    ResponseEntity<List<String>> chargementFichierBancaire(
            @ApiParam(hidden = true) @RequestParam("files") List<MultipartFile> multipartFiles,
            @RequestParam("idBanque") Integer idBanque,
            @RequestParam("matricule") String matricule);

    @Operation(tags = "Importations")
    @PutMapping(path = "/importation/enrichir-banc/{idImportation}")
    ResponseEntity<List<EnrMouvement>> enrichirFichierBancaire(@PathVariable Integer idImportation);

    @Operation(tags = "Importations")
    @GetMapping(path = "/importation/download/{idImportation}")
    ResponseEntity<Resource> telechargerFichierBancaire(@PathVariable Integer idImportation) throws IOException;

    @Operation(tags = "Importations")
    @GetMapping(path = "/importation")
    ResponseEntity<List<ImportationResDto>> getAllImportation();

    @Operation(tags = "Importations")
    @DeleteMapping(path = "/importation/{id}")
    ResponseEntity<Void> deleteById(@RequestParam Integer id);

    @Operation(tags = "Importations")
    @GetMapping(path = "/importation/matricule/{matricule}")
    ResponseEntity<List<ImportationResDto>> getImporationByMatricule(@PathVariable String matricule);

    @Operation(tags = "Importations")
    @GetMapping(path = "/importation/last/{matricule}")
    ResponseEntity<Integer> getLastImportationByMatricule(@PathVariable String matricule);
}
