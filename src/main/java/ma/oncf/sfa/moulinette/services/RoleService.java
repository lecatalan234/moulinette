package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.RoleReqDto;
import ma.oncf.sfa.moulinette.dto.RoleResDto;
import ma.oncf.sfa.moulinette.entities.Role;

import java.util.List;

public interface RoleService {
    List<RoleResDto> getAllRoles();
    RoleResDto save(RoleReqDto roleReqDto);
    RoleResDto update(RoleReqDto roleReqDto, Integer id);
    void deleteById(Integer id);
}
