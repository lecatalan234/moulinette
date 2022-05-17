package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.FluxReqDto;
import ma.oncf.sfa.moulinette.dto.FluxResDto;

import java.util.List;

public interface FluxService {
    FluxResDto save(FluxReqDto fluxReqDto);
    FluxResDto update(FluxReqDto fluxReqDto, Integer id);
    List<FluxResDto> getAllflux();
    void deleteById(Integer id);
    FluxResDto getFluxById(Integer id);
}
