package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.FichierReqDto;
import ma.oncf.sfa.moulinette.dto.FichierResDto;
import ma.oncf.sfa.moulinette.entities.Fichier;
import ma.oncf.sfa.moulinette.mapper.FichierMapper;
import ma.oncf.sfa.moulinette.repositories.FichierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichierServiceImpl implements FichierService {

    @Autowired
    private FichierRepository fichierRepository;
    @Autowired
    private FichierMapper fichierMapper;

    @Override
    public FichierResDto save(FichierReqDto fichierReqDto) {
        Fichier fichier = fichierMapper.fichierdtoToFichier(fichierReqDto);
        return fichierMapper.fichierToFichierDto(fichierRepository.save(fichier));
    }
}
