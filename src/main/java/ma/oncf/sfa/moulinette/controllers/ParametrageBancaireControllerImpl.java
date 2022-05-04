package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.ParametrageBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageBancaireResDto;
import ma.oncf.sfa.moulinette.services.ParametrageBancaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@SecurityRequirement(name = "moulinette-api")
public class ParametrageBancaireControllerImpl implements ParametrageBancaireController {

    @Autowired
    private ParametrageBancaireService parametrageBancaireService;

    @Override
    public ResponseEntity<List<ParametrageBancaireResDto>> getAllParamBancaire() {
        return ResponseEntity.status(HttpStatus.OK).body(parametrageBancaireService.getAllParamBancaire());
    }

    @Override
    public ResponseEntity<ParametrageBancaireResDto> save(ParametrageBancaireReqDto parametrageBancaireReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(parametrageBancaireService.save(parametrageBancaireReqDto));
    }

    @Override
    public ResponseEntity<ParametrageBancaireResDto> update(ParametrageBancaireReqDto parametrageBancaireReqDto, Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(parametrageBancaireService.update(parametrageBancaireReqDto, id));
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        parametrageBancaireService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ParametrageBancaireResDto>> getAllParamBancaireWithCondition() {
        return ResponseEntity.status(HttpStatus.OK).body(parametrageBancaireService.getAllParamBancaireWithCondition());
    }

    @Override
    public ResponseEntity<ParametrageBancaireResDto> changerEtatParamBancaire(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(parametrageBancaireService.changerEtatParamBancaire(id));
    }
}
