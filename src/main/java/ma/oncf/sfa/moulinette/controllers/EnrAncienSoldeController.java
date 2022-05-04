package ma.oncf.sfa.moulinette.controllers;


import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.entities.EnrAncienSolde;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface EnrAncienSoldeController {

    @GetMapping(path = "/releves")
    ResponseEntity<List<EnrAncienSolde>> getAllEnregistrements();
}
