package ma.oncf.sfa.moulinette.controllers;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import ma.oncf.sfa.moulinette.dto.RoleReqDto;
import ma.oncf.sfa.moulinette.dto.RoleResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Roles")
public interface RoleController {

    @Operation(tags = "Roles")
    @GetMapping(path = "/roles")
    ResponseEntity<List<RoleResDto>> getAllRoles();

    @Operation(tags = "Roles")
    @PostMapping(path = "/roles")
    ResponseEntity<RoleResDto> save(@RequestBody RoleReqDto roleReqDto);

    @Operation(tags = "Roles")
    @PutMapping(path = "/roles/{id}")
    ResponseEntity<RoleResDto> update(@RequestBody RoleReqDto roleReqDto, @PathVariable Integer id);

    @Operation(tags = "Roles")
    @DeleteMapping(path = "/roles/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id);

}
