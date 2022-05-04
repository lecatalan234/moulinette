package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.RoleReqDto;
import ma.oncf.sfa.moulinette.dto.RoleResDto;
import ma.oncf.sfa.moulinette.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role roleDtoToRole(RoleReqDto roleReqDto);
    RoleResDto roleToRoleDto(Role role);
}
