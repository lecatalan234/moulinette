package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.entities.EnrAncienSolde;

import java.util.List;

public interface EnrAncienSoldeService {
    List<EnrAncienSolde> getAllEnregistrements();
    EnrAncienSolde save(EnrAncienSolde enrAncienSolde);
}
