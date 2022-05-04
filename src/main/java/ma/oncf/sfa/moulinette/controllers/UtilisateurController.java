package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("Utilisateurs")
public interface UtilisateurController {

    @Operation(tags = "Utilisateurs")
    @GetMapping(path = "/utilisateurs")
    ResponseEntity<List<UtilisateurResDto>> getAllUtilisateurs();

    @Operation(tags = "Utilisateurs")
    @PostMapping(path = "/utilisateurs")
    ResponseEntity<UtilisateurResDto> save(@Valid @RequestBody UtilisateurReqDto utilisateurReqDto);

    @Operation(tags = "Utilisateurs")
    @PutMapping(path = "/utilisateurs/{id}")
    ResponseEntity<UtilisateurUpdateResDto> update(@Valid @RequestBody UtilisateurUpdateReqDto utilisateurUpdateReqDto, @PathVariable Integer id);

    @Operation(tags = "Utilisateurs")
    @DeleteMapping(path = "/utilisateurs/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id);

    @Operation(tags = "Utilisateurs")
    @GetMapping(path = "/utilisateurs/matricule/{matricule}")
    ResponseEntity<UtilisateurResDto> getUtilisateurByMatricule(@PathVariable String matricule);

    @Operation(tags = "Utilisateurs")
    @GetMapping(path = "/utilisateurs/{id}")
    ResponseEntity<UtilisateurResDto> getUtilisateurById(@PathVariable Integer id);

    @Operation(tags = "Utilisateurs")
    @PutMapping(path = "/utilisateurs/reset-password")
    ResponseEntity<UtilisateurResDto> changerMotDePasse(@Valid @RequestBody ChangerMotDePasseDto changerMotDePasseDto);
}
