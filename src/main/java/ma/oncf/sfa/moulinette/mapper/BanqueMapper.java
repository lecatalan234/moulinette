package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.BanqueReqDto;
import ma.oncf.sfa.moulinette.dto.BanqueResDto;
import ma.oncf.sfa.moulinette.entities.Banque;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BanqueMapper {
    Banque banqueDtoToBanque(BanqueReqDto banqueReqDto);
    BanqueResDto banqueToBanqueDto(Banque banque);
}
