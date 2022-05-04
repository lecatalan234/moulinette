package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.BanqueReqDto;
import ma.oncf.sfa.moulinette.dto.BanqueResDto;
import ma.oncf.sfa.moulinette.entities.Banque;
import ma.oncf.sfa.moulinette.mapper.BanqueMapper;
import ma.oncf.sfa.moulinette.repositories.BanqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BanqueServiceImpl implements BanqueService {

    @Autowired
    private BanqueMapper banqueMapper;
    @Autowired
    private BanqueRepository banqueRepository;

    @Override
    public BanqueResDto save(BanqueReqDto banqueReqDto) {
        Banque banque = banqueMapper.banqueDtoToBanque(banqueReqDto);
        return banqueMapper.banqueToBanqueDto(banqueRepository.save(banque));
    }

    @Override
    public BanqueResDto update(BanqueReqDto banqueReqDto, Integer id) {
        banqueReqDto.setId(id);
        Banque banque = banqueMapper.banqueDtoToBanque(banqueReqDto);
        return banqueMapper.banqueToBanqueDto(banqueRepository.save(banque));
    }

    @Override
    public void deleteById(Integer id) {
        banqueRepository.deleteById(id);
    }

    @Override
    public List<BanqueResDto> getAllBanques() {
        List<Banque> banques = banqueRepository.findAll();
        return banques.stream().map(banque -> banqueMapper.banqueToBanqueDto(banque)).collect(Collectors.toList());
    }
}
