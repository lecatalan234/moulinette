package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.FluxReqDto;
import ma.oncf.sfa.moulinette.dto.FluxResDto;
import ma.oncf.sfa.moulinette.entities.Flux;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FluxMapper {
    Flux fluxDtoToflux(FluxReqDto fluxReqDto);
    FluxResDto fluxToFluxDto(Flux flux);
}
