package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ParametrageComptableReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageComptableResDto;
import ma.oncf.sfa.moulinette.entities.ParametrageComptable;
import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.mapper.ParametrageComptableMapper;
import ma.oncf.sfa.moulinette.repositories.ParametrageComptableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParametrageCompatbleServiceImpl implements ParametrageCompatbleService {

    @Autowired
    private ParametrageComptableRepository parametrageComptableRepository;
    @Autowired
    ParametrageComptableMapper parametrageComptableMapper;

    @Override
    public List<ParametrageComptableResDto> getAllParamComptable() {
        List<ParametrageComptable> parametrageComptables = parametrageComptableRepository.findAll();
        return parametrageComptables.stream().map(param -> parametrageComptableMapper.paramComptableToParamComptableDto(param))
                .collect(Collectors.toList());
    }

    @Override
    public ParametrageComptableResDto save(ParametrageComptableReqDto parametrageComptableReqDto) {
        ParametrageComptable parametrageComptable = parametrageComptableMapper.paramComptableDtoToParamComptable(parametrageComptableReqDto);
        return parametrageComptableMapper.paramComptableToParamComptableDto(parametrageComptableRepository.save(parametrageComptable));
    }

    @Override
    public ParametrageComptableResDto update(ParametrageComptableReqDto parametrageComptableReqDto, Integer id) {
        parametrageComptableReqDto.setId(id);
        ParametrageComptable parametrageComptable = parametrageComptableMapper.paramComptableDtoToParamComptable(parametrageComptableReqDto);
        return parametrageComptableMapper.paramComptableToParamComptableDto(parametrageComptableRepository.save(parametrageComptable));
    }

    @Override
    public void deleteById(Integer id) {
        parametrageComptableRepository.deleteById(id);
    }

    @Override
    public List<ParametrageComptableResDto> getAllParamComptableWithCondition() {

        List<ParametrageComptable> parametrageComptables = parametrageComptableRepository.getAllParamBancaireWithCondition();
        //supprimer les doublons
        parametrageComptables = parametrageComptables.stream().distinct().collect(Collectors.toList());
        return parametrageComptables.stream().map(param -> parametrageComptableMapper.paramComptableToParamComptableDto(param))
                .collect(Collectors.toList());
    }

    @Override
    public ParametrageComptableResDto changerEtatParamComptable(Integer id) {

        ParametrageComptable paramComptable = parametrageComptableRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Parametrage comptable non trouve"));
        paramComptable.setId(id);
        if(paramComptable.getActif()) paramComptable.setActif(false);
        else paramComptable.setActif(true);

        return parametrageComptableMapper.paramComptableToParamComptableDto(parametrageComptableRepository.save(paramComptable));
    }
}
