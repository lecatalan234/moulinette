package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.AuthReqDto;
import ma.oncf.sfa.moulinette.dto.AuthResDto;
import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.security.utils.JwtUtil;
import ma.oncf.sfa.moulinette.services.ApplicationUserDetailsService;
import ma.oncf.sfa.moulinette.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/")
@SecurityRequirement(name = "moulinette-api")
public class AuthControllerImpl implements AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public ResponseEntity<AuthResDto> seconneter(AuthReqDto authReqDto) {
        Boolean etatCompte = false;
        try{
            etatCompte= utilisateurService.getUtilisateurByMatricule(authReqDto.getMatricule()).getEtatCompte();
        }catch (Exception e){
            throw new EntityNotFoundException("Matricule et/ou Mot de passe incorrect");
        }

        if(etatCompte){
            try{
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authReqDto.getMatricule(),
                                authReqDto.getMotDePasse()
                        )
                );

                final UserDetails userDetails = userDetailsService.loadUserByUsername(authReqDto.getMatricule());

                final String jwt = jwtUtil.generateToken((User) userDetails);
                AuthResDto authResDto = new AuthResDto();
                authResDto.setToken(jwt);
                return ResponseEntity.ok().body(authResDto);
            }catch (Exception e){
                throw new EntityNotFoundException("Matricule et/ou Mot de passe incorrect");
            }
        }
        else throw new EntityNotFoundException("Votre compte est desactive. Merci de contacter l'administrateur");
    }
}
