package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.*;
import ma.oncf.sfa.moulinette.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/")
@SecurityRequirement(name = "moulinette-api")
public class UtilisateurControllerImpl implements UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public ResponseEntity<List<UtilisateurResDto>> getAllUtilisateurs() {
        return ResponseEntity.status(HttpStatus.OK).body(utilisateurService.getAllUtilisateurs());
    }

    @Override
    public ResponseEntity<UtilisateurResDto> save(UtilisateurReqDto utilisateurReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(utilisateurService.save(utilisateurReqDto));
    }

    @Override
    public ResponseEntity<UtilisateurUpdateResDto> update(UtilisateurUpdateReqDto utilisateurUpdateReqDto, Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(utilisateurService.update(utilisateurUpdateReqDto, id));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        utilisateurService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<UtilisateurResDto> getUtilisateurByMatricule(String matricule) {
        return ResponseEntity.status(HttpStatus.OK).body(utilisateurService.getUtilisateurByMatricule(matricule));
    }

    @Override
    public ResponseEntity<UtilisateurResDto> getUtilisateurById(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(utilisateurService.getUtilisateurById(id));
    }

    @Override
    public ResponseEntity<UtilisateurResDto> changerMotDePasse(ChangerMotDePasseDto changerMotDePasseDto) {
        return ResponseEntity.status(HttpStatus.OK).body(utilisateurService.changerMotDePasse(changerMotDePasseDto));
    }
}
