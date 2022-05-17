package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.BanqueReqDto;
import ma.oncf.sfa.moulinette.dto.BanqueResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("Banques")
public interface BanqueController {
    @Operation(tags = "banques")
    @PostMapping(path = "/banques")
    ResponseEntity<BanqueResDto> save(@Valid @RequestBody BanqueReqDto banqueReqDto);

    @Operation(tags = "banques")
    @PutMapping(path = "/banques/{id}")
    ResponseEntity<BanqueResDto> update(@Valid @RequestBody BanqueReqDto banqueReqDto, @PathVariable Integer id);

    @Operation(tags = "banques")
    @DeleteMapping(path = "/banques/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Integer id);

    @Operation(tags = "banques")
    @GetMapping(path = "/banques")
    ResponseEntity<List<BanqueResDto>> getAllBanques();

    @Operation(tags = "banques")
    @GetMapping(path = "/banques/{id}")
    ResponseEntity<BanqueResDto> getBanqueById(@PathVariable Integer id);
}
