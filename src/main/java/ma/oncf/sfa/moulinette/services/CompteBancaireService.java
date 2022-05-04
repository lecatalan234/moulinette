package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.CompteBancaireReqDto;
import ma.oncf.sfa.moulinette.dto.CompteBancaireResDto;

import java.util.List;

public interface CompteBancaireService {
    List<CompteBancaireResDto> getAllComptesBancaires();
    CompteBancaireResDto save(CompteBancaireReqDto compteBancaireReqDto);
    CompteBancaireResDto update(CompteBancaireReqDto compteBancaireReqDto, Integer id);
    void deleteById(Integer id);
    CompteBancaireResDto getCompteBancaireById(Integer id);
}
