package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.CompteBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.CompteBancaireResDto;
import ma.oncf.sfa.moulinette.entities.CompteBancaire;
import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.mapper.CompteBancaireMapper;
import ma.oncf.sfa.moulinette.repositories.CompteBancaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompteBancaireServiceImpl implements CompteBancaireService {
    @Autowired
    private CompteBancaireRepository compteBancaireRepository;
    @Autowired
    private CompteBancaireMapper compteBancaireMapper;

    @Override
    public List<CompteBancaireResDto> getAllComptesBancaires() {
        List<CompteBancaire> compteBancaires = compteBancaireRepository.findAll();
        return compteBancaires.stream().map(compte -> compteBancaireMapper.compteBancaireToCompteBancaireDto(compte))
                .collect(Collectors.toList());
    }

    @Override
    public CompteBancaireResDto save(CompteBancaireReqDto compteBancaireReqDto) {
        CompteBancaire compteBancaire = compteBancaireRepository.save(compteBancaireMapper.compteBancaireDtoToCompteBancaire(compteBancaireReqDto));
        return compteBancaireMapper.compteBancaireToCompteBancaireDto(compteBancaire);
    }

    @Override
    public CompteBancaireResDto update(CompteBancaireReqDto compteBancaireReqDto, Integer id) {
        compteBancaireReqDto.setId(id);
        CompteBancaire compteBancaire = compteBancaireMapper.compteBancaireDtoToCompteBancaire(compteBancaireReqDto);
        return compteBancaireMapper.compteBancaireToCompteBancaireDto(compteBancaireRepository.save(compteBancaire));
    }

    @Override
    public void deleteById(Integer id) {
        compteBancaireRepository.deleteById(id);
    }

    @Override
    public CompteBancaireResDto getCompteBancaireById(Integer id) {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Compte bancaire non trouve"));
        return compteBancaireMapper.compteBancaireToCompteBancaireDto(compteBancaire);
    }
}
