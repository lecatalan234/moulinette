package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.BanqueReqDto;
import ma.oncf.sfa.moulinette.dto.BanqueResDto;
import ma.oncf.sfa.moulinette.services.BanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/")
@SecurityRequirement(name = "moulinette-api")
public class BanqueControllerImpl implements BanqueController {

    @Autowired
    private BanqueService banqueService;

    @Override
    public ResponseEntity<BanqueResDto> save(BanqueReqDto banqueReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(banqueService.save(banqueReqDto));
    }

    @Override
    public ResponseEntity<BanqueResDto> update(BanqueReqDto banqueReqDto, Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(banqueService.update(banqueReqDto, id));
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        banqueService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<BanqueResDto>> getAllBanques() {
        return ResponseEntity.status(HttpStatus.OK).body(banqueService.getAllBanques());
    }
}
