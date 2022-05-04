package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.entities.EnrAncienSolde;
import ma.oncf.sfa.moulinette.repositories.EnrAncienSoldeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrAncienSoldeServiceImpl implements EnrAncienSoldeService {

    @Autowired
    private EnrAncienSoldeRepository enrAncienSoldeRepository;

    @Override
    public List<EnrAncienSolde> getAllEnregistrements() {
        return enrAncienSoldeRepository.findAll();
    }

    @Override
    public EnrAncienSolde save(EnrAncienSolde enrAncienSolde) {
        return enrAncienSoldeRepository.save(enrAncienSolde);
    }
}
