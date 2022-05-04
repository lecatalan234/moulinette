package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ImportationReqDto;
import ma.oncf.sfa.moulinette.dto.ImportationResDto;
import ma.oncf.sfa.moulinette.entities.*;
import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.mapper.ImportationMapper;
import ma.oncf.sfa.moulinette.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static ma.oncf.sfa.moulinette.utils.Constants.SENS;

@Service
@Transactional
public class ImportationServiceImpl implements ImportationService {

    @Autowired
    private ImportationRepository importationRepository;
    @Autowired
    private ImportationMapper importationMapper;
    @Autowired
    private FichierRepository fichierRepository;
    @Autowired
    private EnrAncienSoldeRepository enrAncienSoldeRepository;
    @Autowired
    private EnrNouveauSoldeRepository enrNouveauSoldeRepository;
    @Autowired
    private EnrMouvementRepository enrMouvementRepository;
    @Autowired
    private EnrComplementRepository enrComplementRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ParametrageBancaireRepository parametrageBancaireRepository;

    @Override
    public List<String> chargementFichierBancaire(List<MultipartFile> multipartFiles, Integer idBanque, String matricule) {
        String PATH_UPLOAD_BANK = "./moulinetteFiles/uploads/";

        if(!multipartFiles.isEmpty()){
            Importation importation = new Importation();
            Banque banque = new Banque();
            Utilisateur utilisateur = utilisateurRepository.findUtilisateurByMatricule(matricule);
            Integer idImprort;

            ImportationReqDto importationReqDto = new ImportationReqDto();
            banque.setId(idBanque);
            importationReqDto.setBanque(banque);
            importationReqDto.setUtilisateur(utilisateur);
            importationReqDto.setTypeImportation("B");
            idImprort = this.save(importationReqDto).getId();
            importation.setId(idImprort);

            //Parcourir les fichiers envoye par client
            List<String> filenames = new ArrayList<>();
            try {
                for (MultipartFile file : multipartFiles){
                    String filename = StringUtils.cleanPath(file.getOriginalFilename());
                    Files.copy(file.getInputStream(),
                            Paths.get(PATH_UPLOAD_BANK + importation.getId()+ "_" + file.getOriginalFilename()),
                            StandardCopyOption.REPLACE_EXISTING);
                    filenames.add(filename);
                    Fichier fichier = new Fichier();
                    fichier.setImportation(importation);
                    fichier.setNomFichier(importation.getId()+"_"+file.getOriginalFilename());
                    fichier.setTaille(file.getSize());
                    //creation d'un enrigstrement dans la table fichier
                    Fichier savedFichier = fichierRepository.save(fichier);
                    //inserer le contenu du fichier dans la base de donnees
                    this.insererLigne(savedFichier);
                    String path = PATH_UPLOAD_BANK + importation.getId()+ "_" + file.getOriginalFilename();
                    Files.delete(Path.of(path));
                }
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }

            return filenames;
        }
        else throw new EntityNotFoundException("Aucun fichier n'a été sélectionné");

    }

    //Inserer les lignes d'un fichier dans la BD
    private void insererLigne(Fichier fichier){
        String nom_fichier = "./moulinetteFiles/uploads/"+fichier.getNomFichier();
        Integer idEnrAncienSolde = null;
        Integer idEnrMouvement = null;

        try (Scanner scanner = new Scanner(new File(nom_fichier))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                for(int i=0; i<line.length()/120;i++){
                    //Charger les lignes du Solde Initial (commence par 01))
                    if(line.substring(120*i,(120*i)+2).equals("01")){
                        EnrAncienSolde enrAncienSolde = new EnrAncienSolde();
                        enrAncienSolde.setCodeEnregistrement("01");
                        enrAncienSolde.setCodeBanque(line.substring((120*i)+2,(120*i)+7));
                        enrAncienSolde.setZoneReservee1(line.substring((120*i)+7,(120*i)+11));
                        enrAncienSolde.setCodeGuichet(line.substring((120*i)+11,(120*i)+16));
                        enrAncienSolde.setCodeDevise(line.substring((120*i)+16,(120*i)+19));
                        enrAncienSolde.setNbrDecimalesMontant(line.substring((120*i)+19,(120*i)+20));
                        enrAncienSolde.setZoneReservee2(line.substring((120*i)+20,(120*i)+21));
                        enrAncienSolde.setNumeroCompte(line.substring((120*i)+21,(120*i)+32));
                        enrAncienSolde.setZoneReservee3(line.substring((120*i)+32,(120*i)+34));
                        enrAncienSolde.setDateSolde(line.substring((120*i)+34,(120*i)+40));
                        enrAncienSolde.setZoneReservee4(line.substring((120*i)+40,(120*i)+90));
                        enrAncienSolde.setMontant(line.substring((120*i)+90,(120*i)+104));
                        enrAncienSolde.setZoneReservee5(line.substring((120*i)+104,(120*i)+120));
                        enrAncienSolde.setFichier(fichier);

                        idEnrAncienSolde = enrAncienSoldeRepository.save(enrAncienSolde).getId();
                    }
                    //Charger les lignes du Solde Final (commence par 07))
                    else if (line.substring(120*i,(120*i)+2).equals("07")){
                        EnrNouveauSolde enrNouveauSolde = new EnrNouveauSolde();
                        EnrAncienSolde enrAncienSolde = new EnrAncienSolde();
                        enrAncienSolde.setId(idEnrAncienSolde);

                        enrNouveauSolde.setEnrAncienSolde(enrAncienSolde);
                        enrNouveauSolde.setCodeEnregistrement("07");
                        enrNouveauSolde.setCodeBanque(line.substring((120*i)+2,(120*i)+7));
                        enrNouveauSolde.setZoneReservee1(line.substring((120*i)+7,(120*i)+11));
                        enrNouveauSolde.setCodeGuichet(line.substring((120*i)+11,(120*i)+16));
                        enrNouveauSolde.setCodeDevise(line.substring((120*i)+16,(120*i)+19));
                        enrNouveauSolde.setNbrDecimalesMontant(line.substring((120*i)+19,(120*i)+20));
                        enrNouveauSolde.setZoneReservee2(line.substring((120*i)+20,(120*i)+21));
                        enrNouveauSolde.setNumeroCompte(line.substring((120*i)+21,(120*i)+32));
                        enrNouveauSolde.setZoneReservee3(line.substring((120*i)+32,(120*i)+34));
                        enrNouveauSolde.setDateSolde(line.substring((120*i)+34,(120*i)+40));
                        enrNouveauSolde.setZoneReservee4(line.substring((120*i)+40,(120*i)+90));
                        enrNouveauSolde.setMontant(line.substring((120*i)+90,(120*i)+104));
                        enrNouveauSolde.setZoneReservee5(line.substring((120*i)+104,(120*i)+120));

                        enrNouveauSoldeRepository.save(enrNouveauSolde);
                    }
                    //Charger les lignes de Mouvement (commence par 04))
                    if(line.substring(120*i,(120*i)+2).equals("04")){
                        EnrMouvement enrMouvement = new EnrMouvement();
                        EnrAncienSolde enrAncienSolde = new EnrAncienSolde();
                        enrAncienSolde.setId(idEnrAncienSolde);

                        enrMouvement.setEnrAncienSolde(enrAncienSolde);
                        enrMouvement.setCodeEnregistrement("04");
                        enrMouvement.setCodeBanque(line.substring((120*i)+2,(120*i)+7));
                        enrMouvement.setCoi(line.substring((120*i)+7,(120*i)+11));
                        enrMouvement.setCodeGuichet(line.substring((120*i)+11,(120*i)+16));
                        enrMouvement.setCodeDevise(line.substring((120*i)+16,(120*i)+19));
                        enrMouvement.setNbrDecimalesMontant(line.substring((120*i)+19,(120*i)+20));
                        enrMouvement.setZoneReservee1(line.substring((120*i)+20,(120*i)+21));
                        enrMouvement.setNumeroCompte(line.substring((120*i)+21,(120*i)+32));
                        enrMouvement.setCib(line.substring((120*i)+32,(120*i)+34));
                        enrMouvement.setDateOperation(line.substring((120*i)+34,(120*i)+40));
                        enrMouvement.setCodeMotifRejet(line.substring((120*i)+40,(120*i)+42));
                        enrMouvement.setDateValeur(line.substring((120*i)+42,(120*i)+48));
                        enrMouvement.setLibelle(line.substring((120*i)+48,(120*i)+79));
                        enrMouvement.setZoneReservee2(line.substring((120*i)+79,(120*i)+81));
                        enrMouvement.setNumEcriture(line.substring((120*i)+81,(120*i)+88));
                        enrMouvement.setIndiceExoCommession(line.substring((120*i)+88,(120*i)+89));
                        enrMouvement.setIndiceIndisponibilite(line.substring((120*i)+89,(120*i)+90));
                        enrMouvement.setMontant(line.substring((120*i)+90,(120*i)+104));
                        enrMouvement.setZoneReference(line.substring((120*i)+104,(120*i)+120));
                        enrMouvement.setSens( sensMontant(line.substring((120*i)+90,(120*i)+104)));

                        idEnrMouvement = enrMouvementRepository.save(enrMouvement).getId();
                    }
                    //Charger les lignes Complement (commence par 05))
                    if(line.substring(120*i,(120*i)+2).equals("05")){
                        EnrComplement enrComplement = new EnrComplement();
                        EnrMouvement enrMouvement = new EnrMouvement();
                        enrMouvement.setId(idEnrMouvement);

                        enrComplement.setEnrMvt(enrMouvement);
                        enrComplement.setCodeEnregistrement("05");
                        enrComplement.setCodeBanque(line.substring((120*i)+2,(120*i)+7));
                        enrComplement.setCoi(line.substring((120*i)+7,(120*i)+11));
                        enrComplement.setCodeGuichet(line.substring((120*i)+11,(120*i)+16));
                        enrComplement.setCodeDevise(line.substring((120*i)+16,(120*i)+19));
                        enrComplement.setNbrDecimalesMontant(line.substring((120*i)+19,(120*i)+20));
                        enrComplement.setZoneReservee1(line.substring((120*i)+20,(120*i)+21));
                        enrComplement.setNumeroCompte(line.substring((120*i)+21,(120*i)+32));
                        enrComplement.setCib(line.substring((120*i)+32,(120*i)+34));
                        enrComplement.setDateOperation(line.substring((120*i)+34,(120*i)+40));
                        enrComplement.setZoneReservee2(line.substring((120*i)+40,(120*i)+45));
                        enrComplement.setQualifiantZone(line.substring((120*i)+45,(120*i)+48));
                        enrComplement.setInfoComplementaire(line.substring((120*i)+48,(120*i)+118));
                        enrComplement.setZoneReservee3(line.substring((120*i)+118,(120*i)+120));

                        enrComplementRepository.save(enrComplement);
                    }
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EnrMouvement> enrichirFichierBancaire(Integer idImportation) {
        //String update_requete = "";
        String select_requete = "";
        List<EnrMouvement> enrMouvementList = new ArrayList<EnrMouvement>();
        List<EnrMouvement> enrMouvementListAll = new ArrayList<EnrMouvement>();
        EnrMouvement enrMouvement = new EnrMouvement();

        //recuperer la banque a partir d une importation
        Importation importation = this.importationRepository.findById(idImportation).orElseThrow(()->new EntityNotFoundException("Importation non trouve"));
        Banque banque = importation.getBanque();

        //recuperer la liste des parametrages bancaires d' une banque
        List<ParametrageBancaire> paramBancaire = this.parametrageBancaireRepository.findParametrageBancaireByBanque(banque);
        for(ParametrageBancaire p : paramBancaire){
            if(p.getActif()){
                select_requete = "SELECT * FROM enr_mouvement "+
                        "WHERE enr_ancien_solde_id IN (SELECT id from enr_ancien_solde where fichier_id IN (SELECT id from fichier where importation_id ='"+idImportation+"')) "+
                        "AND cib = '" + p.getCib() + "' AND ";

                for(Condition c: p.getConditions()){
                    select_requete = select_requete + " " +c.getConditionSQL();
                }

                //Recuperer les mouvements selon la condition
                enrMouvementList = this.importationRepository.selectMouvementCibUpdate(select_requete);

                //Parcourir les mouvements pour mettre a jour le CIB
                for(EnrMouvement mouvement : enrMouvementList){
                    enrMouvement = enrMouvementRepository.findById(mouvement.getId()).orElseThrow(()->new EntityNotFoundException("Mouvement non trouvé"));
                    enrMouvement.setId(mouvement.getId());
                    enrMouvement.setNouveauCib(p.getNouveauCib());
                    enrMouvementRepository.save(enrMouvement);
                }

                //Recuperer les mouvements retournes par toutes les conditions du parametrage
                enrMouvementListAll.addAll(enrMouvementList);
            }
        }
        this.creerFichierBancaireEnrichi(idImportation);

        return enrMouvementListAll;
    }

    private void creerFichierBancaireEnrichi(Integer idImportation){

        try {
            Importation importation = this.importationRepository.findById(idImportation).orElseThrow(()->new EntityNotFoundException("Importation non trouve"));
            Banque banque = importation.getBanque();
            Date date = new Date();
            String PATH_DOWNLOAD_BANK = "./moulinetteFiles/downloads/";
            String outFileName = importation.getId()+"_"+banque.getCodeBanque()+"_"+ date.getTime()/1000;

            // create a writer
            FileOutputStream fos = new FileOutputStream(PATH_DOWNLOAD_BANK + outFileName + ".txt");
            OutputStreamWriter writer = new OutputStreamWriter(fos, Charset.forName("UTF_16"));
            String CIB = "";

            List<EnrAncienSolde> lignes = enrAncienSoldeRepository.getAllMouvementsbyImporation(idImportation);

            for ( EnrAncienSolde ligne : lignes){
                //Enrigistrement 01
                writer.write(ligne.getCodeEnregistrement());
                writer.write(ligne.getCodeBanque());
                writer.write(ligne.getZoneReservee1());
                writer.write(ligne.getCodeGuichet());
                writer.write(ligne.getCodeDevise());
                writer.write(ligne.getNbrDecimalesMontant());
                writer.write(ligne.getZoneReservee2());
                writer.write(ligne.getNumeroCompte());
                writer.write(ligne.getZoneReservee3());
                writer.write(ligne.getDateSolde());
                writer.write(ligne.getZoneReservee4());
                writer.write(ligne.getMontant());
                writer.write(ligne.getZoneReservee5());
                writer.write("\n");
                //Enregistrement 04
                for (EnrMouvement mvt : ligne.getEnregistrementMouvements()){
                    //Verifier si le CIB est modifie
                    if(mvt.getNouveauCib() == null) CIB = mvt.getCib();
                    else CIB = mvt.getNouveauCib();

                    writer.write(mvt.getCodeEnregistrement());
                    writer.write(mvt.getCodeBanque());
                    writer.write(mvt.getCoi());
                    writer.write(mvt.getCodeGuichet());
                    writer.write(mvt.getCodeDevise());
                    writer.write(mvt.getNbrDecimalesMontant());
                    writer.write(mvt.getZoneReservee1());
                    writer.write(mvt.getNumeroCompte());
                    writer.write(CIB);
                    writer.write(mvt.getDateOperation());
                    writer.write(mvt.getCodeMotifRejet());
                    writer.write(mvt.getDateValeur());
                    writer.write(mvt.getLibelle());
                    writer.write(mvt.getZoneReservee2());
                    writer.write(mvt.getNumEcriture());
                    writer.write(mvt.getIndiceExoCommession());
                    writer.write(mvt.getIndiceIndisponibilite());
                    writer.write(mvt.getMontant());
                    writer.write(mvt.getZoneReference());
                    writer.write("\n");

                    //Enregistrement 05
                    for(EnrComplement complement: mvt.getEnregistrementComplements()){
                        writer.write(complement.getCodeEnregistrement());
                        writer.write(complement.getCodeBanque());
                        writer.write(complement.getCoi());
                        writer.write(complement.getCodeGuichet());
                        writer.write(complement.getCodeDevise());
                        writer.write(complement.getNbrDecimalesMontant());
                        writer.write(complement.getZoneReservee1());
                        writer.write(complement.getNumeroCompte());
                        writer.write(CIB);
                        writer.write(complement.getDateOperation());
                        writer.write(complement.getZoneReservee2());
                        writer.write(complement.getQualifiantZone());
                        writer.write(complement.getInfoComplementaire());
                        writer.write(complement.getZoneReservee3());
                        writer.write("\n");
                    }
                }
                //Enregistrement 07
                writer.write(ligne.getEnregistrementNouveauSolde().getCodeEnregistrement());
                writer.write(ligne.getEnregistrementNouveauSolde().getCodeBanque());
                writer.write(ligne.getEnregistrementNouveauSolde().getZoneReservee1());
                writer.write(ligne.getEnregistrementNouveauSolde().getCodeGuichet());
                writer.write(ligne.getEnregistrementNouveauSolde().getCodeDevise());
                writer.write(ligne.getEnregistrementNouveauSolde().getNbrDecimalesMontant());
                writer.write(ligne.getEnregistrementNouveauSolde().getZoneReservee2());
                writer.write(ligne.getEnregistrementNouveauSolde().getNumeroCompte());
                writer.write(ligne.getEnregistrementNouveauSolde().getZoneReservee3());
                writer.write(ligne.getEnregistrementNouveauSolde().getDateSolde());
                writer.write(ligne.getEnregistrementNouveauSolde().getZoneReservee4());
                writer.write(ligne.getEnregistrementNouveauSolde().getMontant());
                writer.write(ligne.getEnregistrementNouveauSolde().getZoneReservee5());
                writer.write("\n");
            }

            importation.setOutFile(outFileName);
            importationRepository.save(importation);

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public String telechargerFichierBancaire(Integer idImportation){
        Importation importation = this.importationRepository.findById(idImportation)
                .orElseThrow(()->new EntityNotFoundException("Importation non trouve"));
        return importation.getOutFile() + ".txt";
    }

    private String sensMontant(String montant){
        return SENS.get(montant.substring(montant.length() -1));
    }


    @Override
    public ImportationResDto save(ImportationReqDto importationReqDto) {
        Importation importation = importationMapper.importationDtoToImportation(importationReqDto);
        return importationMapper.importationToImportationDto(importationRepository.save(importation));
    }

    @Override
    public List<ImportationResDto> getAllImportation() {
        List<Importation> importations = importationRepository.findAll();
        return importations.stream().map(importation -> importationMapper.importationToImportationDto(importation)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        importationRepository.deleteById(id);
    }

    @Override
    public List<ImportationResDto> getImportationByUtilisateur(String matricule) {
        List<Importation> importations = importationRepository.findImportationByCreatedBy(matricule);
        return importations.stream().map(importation -> importationMapper.importationToImportationDto(importation)).collect(Collectors.toList());
    }

    @Override
    public Integer getLastImportationByMatricule(String matricule) {
        return importationRepository.getLastImportationByCreatedBy(matricule);
    }

}
