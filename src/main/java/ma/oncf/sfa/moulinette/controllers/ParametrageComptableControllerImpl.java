package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.ParametrageComptableReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageComptableResDto;
import ma.oncf.sfa.moulinette.services.ParametrageCompatbleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@SecurityRequirement(name = "moulinette-api")
public class ParametrageComptableControllerImpl implements ParametrageComptableController {
    @Autowired
    private ParametrageCompatbleService parametrageCompatbleService;

    @Override
    public ResponseEntity<List<ParametrageComptableResDto>> getAllParamComptable() {
        return ResponseEntity.status(HttpStatus.OK).body(parametrageCompatbleService.getAllParamComptable());
    }

    @Override
    public ResponseEntity<ParametrageComptableResDto> save(ParametrageComptableReqDto parametrageComptableReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(parametrageCompatbleService.save(parametrageComptableReqDto));
    }

    @Override
    public ResponseEntity<ParametrageComptableResDto> update(ParametrageComptableReqDto parametrageComptableReqDto, Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(parametrageCompatbleService.update(parametrageComptableReqDto,id));
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        parametrageCompatbleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ParametrageComptableResDto>> getAllParamComptableWithCondition() {
        return ResponseEntity.status(HttpStatus.OK).body(parametrageCompatbleService.getAllParamComptableWithCondition());
    }

    @Override
    public ResponseEntity<ParametrageComptableResDto> changerEtatParamComptable(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(parametrageCompatbleService.changerEtatParamComptable(id));
    }
}
