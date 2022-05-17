package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.FluxReqDto;
import ma.oncf.sfa.moulinette.dto.FluxResDto;
import ma.oncf.sfa.moulinette.entities.Flux;
import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.mapper.FluxMapper;
import ma.oncf.sfa.moulinette.repositories.FluxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FluxServiceImpl implements FluxService {
    @Autowired
    private FluxRepository fluxRepository;
    @Autowired
    private FluxMapper fluxMapper;

    @Override
    public FluxResDto save(FluxReqDto fluxReqDto) {
        Flux flux = fluxMapper.fluxDtoToflux(fluxReqDto);
        return fluxMapper.fluxToFluxDto(fluxRepository.save(flux));
    }

    @Override
    public FluxResDto update(FluxReqDto fluxReqDto, Integer id) {
        fluxReqDto.setId(id);
        Flux flux = fluxMapper.fluxDtoToflux(fluxReqDto);
        return fluxMapper.fluxToFluxDto(fluxRepository.save(flux));
    }

    @Override
    public List<FluxResDto> getAllflux() {
        List<Flux> listeflux = fluxRepository.findAll();
        return listeflux.stream().map(f -> fluxMapper.fluxToFluxDto(f)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        fluxRepository.deleteById(id);
    }

    @Override
    public FluxResDto getFluxById(Integer id) {
        Flux flux = fluxRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Flux comptable non trouve"));
        return fluxMapper.fluxToFluxDto(flux);
    }
}
