package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.*;
import ma.oncf.sfa.moulinette.entities.Utilisateur;
import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.mapper.UtilisateurMapper;
import ma.oncf.sfa.moulinette.mapper.UtilisateurUpdateMapper;
import ma.oncf.sfa.moulinette.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    UtilisateurMapper utilisateurMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtilisateurUpdateMapper utilisateurUpdateMapper;

    @Override
    public List<UtilisateurResDto> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream().map(utilisateur -> utilisateurMapper.utilisateurToUtilsateurDTO(utilisateur)).collect(Collectors.toList());
    }

    @Override
    public UtilisateurResDto save(UtilisateurReqDto utilisateurReqDto) {
        Utilisateur utilisateur = utilisateurMapper.utilisateurDtoToUtilisateur(utilisateurReqDto);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurMapper.utilisateurToUtilsateurDTO(utilisateurRepository.save(utilisateur));
    }

    @Override
    public UtilisateurUpdateResDto update(UtilisateurUpdateReqDto utilisateurUpdateReqDto, Integer id) {
        Utilisateur user = utilisateurRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Utilisateur non trouvé"));

        utilisateurUpdateReqDto.setId(id);
        Utilisateur utilisateur = utilisateurUpdateMapper.utilisateurUpdateDtoToUtilisateur(utilisateurUpdateReqDto);
        utilisateur.setMotDePasse(user.getMotDePasse());
        return utilisateurUpdateMapper.utilisateurToUtilsateurUpdateDTO(utilisateurRepository.save(utilisateur));
    }

    @Override
    public void deleteById(Integer id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurResDto getUtilisateurByMatricule(String matricule) {
        return utilisateurMapper.utilisateurToUtilsateurDTO(utilisateurRepository.findUtilisateurByMatricule(matricule));
    }

    @Override
    public UtilisateurResDto getUtilisateurById(Integer id) {
        return utilisateurMapper.utilisateurToUtilsateurDTO(utilisateurRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Utilisateur non trouvé")));
    }

    @Override
    public UtilisateurResDto changerMotDePasse(ChangerMotDePasseDto changerMotDePasseDto) {

        if(changerMotDePasseDto.getMotDePasse().equals(changerMotDePasseDto.getConfirmMotDePasse())){
            Utilisateur utilisateur = utilisateurRepository.findById(changerMotDePasseDto.getId())
                    .orElseThrow(()->new EntityNotFoundException("Utilisateur non trouvé"));

            utilisateur.setMotDePasse(passwordEncoder.encode(changerMotDePasseDto.getMotDePasse()));
            return utilisateurMapper.utilisateurToUtilsateurDTO(utilisateurRepository.save(utilisateur));
        }
        else throw new EntityNotFoundException("Les mots de passe ne sont pas identiques");

    }


}
