package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.CompteBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.CompteBancaireResDto;
import ma.oncf.sfa.moulinette.services.CompteBancaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/")
@SecurityRequirement(name = "moulinette-api")
public class CompteBancaireControllerImpl implements CompteBancaireController {
    @Autowired
    CompteBancaireService compteBancaireService;

    @Override
    public ResponseEntity<List<CompteBancaireResDto>> getAllComptesBancaires() {
        return ResponseEntity.status(HttpStatus.OK).body(compteBancaireService.getAllComptesBancaires());
    }

    @Override
    public ResponseEntity<CompteBancaireResDto> save(CompteBancaireReqDto compteBancaireReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(compteBancaireService.save(compteBancaireReqDto));
    }

    @Override
    public ResponseEntity<CompteBancaireResDto> update(CompteBancaireReqDto compteBancaireReqDto, Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(compteBancaireService.update(compteBancaireReqDto, id));
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        compteBancaireService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CompteBancaireResDto> getCompteBancaireById(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(compteBancaireService.getCompteBancaireById(id));
    }
}
