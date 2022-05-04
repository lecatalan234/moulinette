package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.*;

import java.util.List;

public interface UtilisateurService {
    List<UtilisateurResDto> getAllUtilisateurs();
    UtilisateurResDto save(UtilisateurReqDto utilisateurReqDto);
    UtilisateurUpdateResDto update(UtilisateurUpdateReqDto utilisateurUpdateReqDto, Integer id);
    void deleteById(Integer id);
    UtilisateurResDto getUtilisateurByMatricule(String matricule);
    UtilisateurResDto getUtilisateurById(Integer id);
    UtilisateurResDto changerMotDePasse(ChangerMotDePasseDto changerMotDePasseDto);

}
