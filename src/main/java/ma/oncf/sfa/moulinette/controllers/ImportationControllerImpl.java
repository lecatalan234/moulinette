package ma.oncf.sfa.moulinette.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.oncf.sfa.moulinette.dto.ImportationResDto;
import ma.oncf.sfa.moulinette.entities.EnrMouvement;
import ma.oncf.sfa.moulinette.services.ImportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "moulinette-api")
public class ImportationControllerImpl implements ImportationController {

    @Autowired
    private ImportationService importationService;

    @Override
    public ResponseEntity<List<String>> chargementFichierBancaire(List<MultipartFile> multipartFiles, Integer idBanque, String matricule) {
        return ResponseEntity.ok().body(importationService.chargementFichierBancaire(multipartFiles, idBanque, matricule));
    }

    @Override
    public ResponseEntity<List<EnrMouvement>> enrichirFichierBancaire(Integer idImportation) {
        return ResponseEntity.ok().body(this.importationService.enrichirFichierBancaire(idImportation));
    }

    @Override
    public ResponseEntity<Resource> telechargerFichierBancaire(Integer idImportation) throws IOException {
        String PATH_DOWNLOAD_BANK = "./moulinetteFiles/downloads/";
        String filename = importationService.telechargerFichierBancaire(idImportation);
        Path filePath = Path.of(PATH_DOWNLOAD_BANK+filename);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " non trouve au niveau du serveur");
        }
        Resource resource = new UrlResource(filePath.toUri());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }

    @Override
    public ResponseEntity<List<ImportationResDto>> getAllImportation() {
        return ResponseEntity.ok().body(importationService.getAllImportation());
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        importationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ImportationResDto>> getImporationByMatricule(String matricule) {
        return ResponseEntity.ok().body(importationService.getImportationByUtilisateur(matricule));
    }

    @Override
    public ResponseEntity<Integer> getLastImportationByMatricule(String matricule) {
        return ResponseEntity.ok().body(importationService.getLastImportationByMatricule(matricule));
    }

}
