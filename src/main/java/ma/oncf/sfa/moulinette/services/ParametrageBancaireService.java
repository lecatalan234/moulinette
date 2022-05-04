package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ParametrageBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.ParametrageBancaireResDto;

import java.util.List;

public interface ParametrageBancaireService {
    List<ParametrageBancaireResDto> getAllParamBancaire();
    ParametrageBancaireResDto save(ParametrageBancaireReqDto parametrageBancaireReqDto);
    ParametrageBancaireResDto update(ParametrageBancaireReqDto parametrageBancaireReqDto, Integer id);
    void deleteById(Integer id);
    List<ParametrageBancaireResDto> getAllParamBancaireWithCondition();
    ParametrageBancaireResDto changerEtatParamBancaire(Integer id);
}
