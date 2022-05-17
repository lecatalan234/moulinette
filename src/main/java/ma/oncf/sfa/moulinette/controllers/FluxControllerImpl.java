package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.FluxReqDto;
import ma.oncf.sfa.moulinette.dto.FluxResDto;
import ma.oncf.sfa.moulinette.services.FluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@SecurityRequirement(name = "moulinette-api")
public class FluxControllerImpl implements FluxController {
    @Autowired
    private FluxService fluxService;

    @Override
    public ResponseEntity<List<FluxResDto>> getAllFlux() {
        return ResponseEntity.status(HttpStatus.OK).body(fluxService.getAllflux());
    }

    @Override
    public ResponseEntity<FluxResDto> save(FluxReqDto fluxReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(fluxService.save(fluxReqDto));
    }

    @Override
    public ResponseEntity<FluxResDto> update(FluxReqDto fluxReqDto, Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(fluxService.update(fluxReqDto,id));
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        fluxService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FluxResDto> getFluxByid(Integer id) {
        return ResponseEntity.ok().body(fluxService.getFluxById(id));
    }
}
