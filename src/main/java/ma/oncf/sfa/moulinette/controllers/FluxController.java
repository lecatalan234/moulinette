package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.FluxReqDto;
import ma.oncf.sfa.moulinette.dto.FluxResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("Flux")
public interface FluxController {
    @Operation(tags = "flux")
    @GetMapping(path = "/flux")
    ResponseEntity<List<FluxResDto>> getAllFlux();

    @Operation(tags = "flux")
    @PostMapping(path = "/flux")
    ResponseEntity<FluxResDto> save(@Valid @RequestBody FluxReqDto fluxReqDto);

    @Operation(tags = "flux")
    @PutMapping(path = "/flux/{id}")
    ResponseEntity<FluxResDto> update(@Valid @RequestBody FluxReqDto fluxReqDto, @PathVariable Integer id);

    @Operation(tags = "flux")
    @DeleteMapping(path = "/flux/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Integer id);

    @Operation(tags = "flux")
    @GetMapping(path = "/flux/{id}")
    ResponseEntity<FluxResDto> getFluxByid(@PathVariable Integer id);
}
