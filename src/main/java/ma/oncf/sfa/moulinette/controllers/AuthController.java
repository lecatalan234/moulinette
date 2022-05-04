package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.AuthReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("Authentification")
public interface AuthController {

    @Operation(tags = "Authentification")
    @PostMapping(path = "/login")
    ResponseEntity<?> seconneter(@RequestBody AuthReqDto authReqDto);
}
