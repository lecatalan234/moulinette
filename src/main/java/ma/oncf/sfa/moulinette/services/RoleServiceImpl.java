package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.RoleReqDto;
import ma.oncf.sfa.moulinette.dto.RoleResDto;
import ma.oncf.sfa.moulinette.entities.Role;
import ma.oncf.sfa.moulinette.mapper.RoleMapper;
import ma.oncf.sfa.moulinette.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleResDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> roleMapper.roleToRoleDto(role)).collect(Collectors.toList());
    }

    @Override
    public RoleResDto save(RoleReqDto roleReqDto) {
        Role role = roleMapper.roleDtoToRole(roleReqDto);
        return roleMapper.roleToRoleDto(roleRepository.save(role));
    }

    @Override
    public RoleResDto update(RoleReqDto roleReqDto, Integer id) {
        roleReqDto.setId(id);
        Role role = roleMapper.roleDtoToRole(roleReqDto);
        return roleMapper.roleToRoleDto(roleRepository.save(role));
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
