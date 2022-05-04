package ma.oncf.sfa.moulinette.controllers;

import ma.oncf.sfa.moulinette.entities.EnrAncienSolde;
import ma.oncf.sfa.moulinette.services.EnrAncienSoldeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EnrAncienSoldeControllerImpl implements EnrAncienSoldeController {

    @Autowired
    private EnrAncienSoldeService enrAncienSoldeService;

    @Override
    public ResponseEntity<List<EnrAncienSolde>> getAllEnregistrements() {
        return ResponseEntity.ok().body(enrAncienSoldeService.getAllEnregistrements());
    }
}
