package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.RoleReqDto;
import ma.oncf.sfa.moulinette.dto.RoleResDto;
import ma.oncf.sfa.moulinette.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@SecurityRequirement(name = "moulinette-api")
public class RoleControllerImpl implements RoleController {
    @Autowired
    private RoleService roleService;
    @Override
    public ResponseEntity<List<RoleResDto>> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRoles());
    }

    @Override
    public ResponseEntity<RoleResDto> save(RoleReqDto roleReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.save(roleReqDto));
    }

    @Override
    public ResponseEntity<RoleResDto> update(RoleReqDto roleReqDto, Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.update(roleReqDto, id));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
