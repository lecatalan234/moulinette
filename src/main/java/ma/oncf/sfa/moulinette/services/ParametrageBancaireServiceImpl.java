package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ParametrageBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageBancaireResDto;
import ma.oncf.sfa.moulinette.entities.ParametrageBancaire;
import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.mapper.ParametrageBancaireMapper;
import ma.oncf.sfa.moulinette.repositories.ParametrageBancaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParametrageBancaireServiceImpl implements ParametrageBancaireService {

    @Autowired
    private ParametrageBancaireRepository parametrageBancaireRepository;
    @Autowired
    private ParametrageBancaireMapper parametrageBancaireMapper;

    @Override
    public List<ParametrageBancaireResDto> getAllParamBancaire() {
        List<ParametrageBancaire> parametrageBancaires = parametrageBancaireRepository.findAll();
        return parametrageBancaires.stream().map(param -> parametrageBancaireMapper.paramBancaireToParamBancaireDto(param))
                .collect(Collectors.toList());
    }

    @Override
    public ParametrageBancaireResDto save(ParametrageBancaireReqDto parametrageBancaireReqDto) {
        ParametrageBancaire parametrageBancaire = parametrageBancaireMapper.paramBancaireDtoToParamBancaire(parametrageBancaireReqDto);
        return parametrageBancaireMapper.paramBancaireToParamBancaireDto(parametrageBancaireRepository.save(parametrageBancaire));
    }

    @Override
    public ParametrageBancaireResDto update(ParametrageBancaireReqDto parametrageBancaireReqDto, Integer id) {
        parametrageBancaireReqDto.setId(id);
        ParametrageBancaire parametrageBancaire = parametrageBancaireMapper.paramBancaireDtoToParamBancaire(parametrageBancaireReqDto);
        return parametrageBancaireMapper.paramBancaireToParamBancaireDto(parametrageBancaireRepository.save(parametrageBancaire));
    }

    @Override
    public void deleteById(Integer id) {
        parametrageBancaireRepository.deleteById(id);
    }

    @Override
    public List<ParametrageBancaireResDto> getAllParamBancaireWithCondition() {
        List<ParametrageBancaire> parametrageBancaires = parametrageBancaireRepository.getAllParamBancaireWithCondition();
       //supprimer les doublons
        parametrageBancaires = parametrageBancaires.stream().distinct().collect(Collectors.toList());
        return parametrageBancaires.stream().map(param -> parametrageBancaireMapper.paramBancaireToParamBancaireDto(param))
                .collect(Collectors.toList());
    }

    @Override
    public ParametrageBancaireResDto changerEtatParamBancaire(Integer id) {
        ParametrageBancaire paramBancaire = this.parametrageBancaireRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Parametrage bancaire non trouve"));
        paramBancaire.setId(id);
        if(paramBancaire.getActif()) paramBancaire.setActif(false);
        else paramBancaire.setActif(true);

        return parametrageBancaireMapper.paramBancaireToParamBancaireDto(this.parametrageBancaireRepository.save(paramBancaire));
    }
}
