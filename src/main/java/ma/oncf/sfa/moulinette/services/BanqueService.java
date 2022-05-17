package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.BanqueReqDto;
import ma.oncf.sfa.moulinette.dto.BanqueResDto;

import java.util.List;

public interface BanqueService {
    BanqueResDto save(BanqueReqDto banqueReqDto);
    BanqueResDto update(BanqueReqDto banqueReqDto, Integer id);
    void deleteById(Integer id);
    List<BanqueResDto> getAllBanques();
    BanqueResDto getBanqueById(Integer id);
}
