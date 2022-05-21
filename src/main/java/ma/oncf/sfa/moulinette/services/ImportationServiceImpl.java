package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ImportationReqDto;
import ma.oncf.sfa.moulinette.dto.ImportationResDto;
import ma.oncf.sfa.moulinette.entities.*;
import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.mapper.ImportationMapper;
import ma.oncf.sfa.moulinette.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
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

import static ma.oncf.sfa.moulinette.utils.Constants.*;

@Service
@Transactional
public class ImportationServiceImpl implements ImportationService {

    Logger logger = LoggerFactory.getLogger(ImportationServiceImpl.class);

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
    @Autowired
    private CompteBancaireRepository compteBancaireRepository;
    @Autowired
    private ComptabiliteRepository comptabiliteRepository;
    @Autowired
    private ParametrageComptableRepository parametrageComptableRepository;

    @Override
    @Transactional
    public List<String> chargementFichierBancaire(List<MultipartFile> multipartFiles, Integer idBanque, String matricule) {
        String pathUploadBank = UPLOAD_PATH;

        //Vider les lignes 01-04-05-07
        this.enrAncienSoldeRepository.deleteAll(enrAncienSoldeRepository.getAllLignesReleveByImporationUser(matricule));

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

            //Parcourir les fichiers envoye par le client
            List<String> filenames = new ArrayList<>();
            try {
                for (MultipartFile file : multipartFiles){
                    String filename = StringUtils.cleanPath(file.getOriginalFilename());
                    Files.copy(file.getInputStream(),
                            Paths.get(pathUploadBank + importation.getId()+ "_" + file.getOriginalFilename()),
                            StandardCopyOption.REPLACE_EXISTING);
                    filenames.add(filename);
                    Fichier fichier = new Fichier();
                    fichier.setImportation(importation);
                    fichier.setNomFichier(importation.getId()+"_"+file.getOriginalFilename());
                    fichier.setTaille(file.getSize());
                    //creation d'un enrigstrement dans la table fichier
                    Fichier savedFichier = fichierRepository.save(fichier);
                    //inserer le contenu du fichier dans la base de donnees
                    this.insererLigne(savedFichier, "B");
                    String path = pathUploadBank + importation.getId()+ "_" + file.getOriginalFilename();
                    Files.delete(Path.of(path));
                }
            }
            catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }

            return filenames;
        }
        else throw new EntityNotFoundException("Aucun fichier n'a été sélectionné");

    }

    @Override
    public List<String> chargementFichierComptable(List<MultipartFile> multipartFiles, String matricule) {
        String pathUploadCompta = UPLOAD_PATH;

        if(!multipartFiles.isEmpty()){
            Importation importation = new Importation();
            Utilisateur utilisateur = utilisateurRepository.findUtilisateurByMatricule(matricule);
            Integer idImprort;

            ImportationReqDto importationReqDto = new ImportationReqDto();
            importationReqDto.setUtilisateur(utilisateur);
            importationReqDto.setTypeImportation("C");
            idImprort = this.save(importationReqDto).getId();
            importation.setId(idImprort);

            //Parcourir les fichiers envoye par le client
            List<String> filenames = new ArrayList<>();

            try {
                for (MultipartFile file : multipartFiles){
                    String filename = StringUtils.cleanPath(file.getOriginalFilename());
                    Files.copy(file.getInputStream(),
                            Paths.get(pathUploadCompta + importation.getId()+ "_" + file.getOriginalFilename()),
                            StandardCopyOption.REPLACE_EXISTING);
                    filenames.add(filename);
                    Fichier fichier = new Fichier();
                    fichier.setImportation(importation);
                    fichier.setNomFichier(importation.getId()+"_"+file.getOriginalFilename());
                    fichier.setTaille(file.getSize());
                    //creation d'un enrigstrement dans la table fichier
                    Fichier savedFichier = fichierRepository.save(fichier);
                    //inserer le contenu du fichier dans la base de donnees
                    this.insererLigne(savedFichier, "C");
                    String path = pathUploadCompta + importation.getId()+ "_" + file.getOriginalFilename();
                    Files.delete(Path.of(path));
                }
            }
            catch (IOException e){
                throw new RuntimeException("");
            }

            return filenames;
        }
        else throw new EntityNotFoundException("Aucun fichier n'a été sélectionné");
    }

    //Inserer les lignes d'un fichier dans la BD
    @Transactional
    private void insererLigne(Fichier fichier, String typeImportation){
        String nomFichier = UPLOAD_PATH+fichier.getNomFichier();

        //B = Importation Bancaire
        if(typeImportation.equals("B")){
            this.insererLigneBancaire(fichier, typeImportation, nomFichier);
        }
        //C= Imporation Comptables
        else this.insererLigneCompta(fichier, typeImportation, nomFichier);
    }

    //Inserer les ligne Bancaire
    private void insererLigneBancaire(Fichier fichier, String typeImportation, String nomFichier){
        Integer idEnrAncienSolde = null;
        Integer idEnrMouvement = null;
        try (Scanner scanner = new Scanner(new File(nomFichier))) {
            int charCode;
            Integer longueurLigne = 0;
            List<String> lignes = new ArrayList<String>();
            String ligne = "";

            try(FileInputStream file = new FileInputStream(nomFichier)){
                //ANSI ENCODAGE
                InputStreamReader input = new InputStreamReader(file,Charset.forName("Cp1252"));

                //Objet utilise seulement pour savoir si le fichier est composent d'une seule ligne ou plusieurs lignes de 120 caracteres
                FileInputStream fstream = new FileInputStream(nomFichier);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


                //Tester si la ligne egal a 120 caractere (donc le fichier est compose de plusieurs lignes de 120 caracteres)
                if(br.readLine().length() == 120){
                    //lire caractere par caractere
                    while ((charCode = input.read()) != -1) {
                        // si le caractere lu egale a un saut de ligne (code char = 10)
                        if(charCode != 10){
                            ligne = ligne + ((char) charCode);
                        }
                        //passer a la ligne suivante
                        else{
                            lignes.add(ligne);
                            ligne = "";
                        }
                    }
                }
                //Dans ce cas le fichier contient seulement une seul ligne qui contient plusieurs types d'enregistrement 01-04-05-07
                else{
                    while ((charCode = input.read()) != -1) {
                        //pour lire seulement 120 caracteres
                        if (longueurLigne<=119){
                            ligne = ligne + ((char) charCode);
                            longueurLigne++;
                            if(!input.ready()){
                                lignes.add(ligne);
                            }
                        }
                        //passer au 120 caracteres suivants
                        else {
                            lignes.add(ligne);
                            ligne = "" + ((char) charCode);
                            longueurLigne = 1;
                        }
                    }

                }
                input.close();
            }catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }


            for(String line : lignes){
                //Charger les lignes du Solde Initial (commence par 01))
                if(line.substring(0,2).equals("01")){
                    EnrAncienSolde enrAncienSolde = new EnrAncienSolde();
                    enrAncienSolde.setCodeEnregistrement("01");
                    enrAncienSolde.setCodeBanque(line.substring(2,7));
                    enrAncienSolde.setZoneReservee1(line.substring(7,11));
                    enrAncienSolde.setCodeGuichet(line.substring(11,16));
                    enrAncienSolde.setCodeDevise(line.substring(16,19));
                    enrAncienSolde.setNbrDecimalesMontant(line.substring(19,20));
                    enrAncienSolde.setZoneReservee2(line.substring(20,21));
                    enrAncienSolde.setNumeroCompte(line.substring(21,32));
                    enrAncienSolde.setZoneReservee3(line.substring(32,34));
                    enrAncienSolde.setDateSolde(line.substring(34,40));
                    enrAncienSolde.setZoneReservee4(line.substring(40,90));
                    enrAncienSolde.setMontant(line.substring(90,104));
                    enrAncienSolde.setZoneReservee5(line.substring(104,120));
                    enrAncienSolde.setFichier(fichier);

                    idEnrAncienSolde = enrAncienSoldeRepository.save(enrAncienSolde).getId();
                }
                //Charger les lignes du Solde Final (commence par 07))
                else if (line.substring(0,2).equals("07")){
                    EnrNouveauSolde enrNouveauSolde = new EnrNouveauSolde();
                    EnrAncienSolde enrAncienSolde = new EnrAncienSolde();
                    enrAncienSolde.setId(idEnrAncienSolde);

                    enrNouveauSolde.setEnrAncienSolde(enrAncienSolde);
                    enrNouveauSolde.setCodeEnregistrement("07");
                    enrNouveauSolde.setCodeBanque(line.substring(2,7));
                    enrNouveauSolde.setZoneReservee1(line.substring(7,11));
                    enrNouveauSolde.setCodeGuichet(line.substring(11,16));
                    enrNouveauSolde.setCodeDevise(line.substring(16,19));
                    enrNouveauSolde.setNbrDecimalesMontant(line.substring(19,20));
                    enrNouveauSolde.setZoneReservee2(line.substring(20,21));
                    enrNouveauSolde.setNumeroCompte(line.substring(21,32));
                    enrNouveauSolde.setZoneReservee3(line.substring(32,34));
                    enrNouveauSolde.setDateSolde(line.substring(34,40));
                    enrNouveauSolde.setZoneReservee4(line.substring(40,90));
                    enrNouveauSolde.setMontant(line.substring(90,104));
                    enrNouveauSolde.setZoneReservee5(line.substring(104,120));

                    enrNouveauSoldeRepository.save(enrNouveauSolde);
                }
                //Charger les lignes de Mouvement (commence par 04))
                if(line.substring(0,2).equals("04")){
                    EnrMouvement enrMouvement = new EnrMouvement();
                    EnrAncienSolde enrAncienSolde = new EnrAncienSolde();
                    enrAncienSolde.setId(idEnrAncienSolde);

                    enrMouvement.setEnrAncienSolde(enrAncienSolde);
                    enrMouvement.setCodeEnregistrement("04");
                    enrMouvement.setCodeBanque(line.substring(2,7));
                    enrMouvement.setCoi(line.substring(7,11));
                    enrMouvement.setCodeGuichet(line.substring(11,16));
                    enrMouvement.setCodeDevise(line.substring(16,19));
                    enrMouvement.setNbrDecimalesMontant(line.substring(19,20));
                    enrMouvement.setZoneReservee1(line.substring(20,21));
                    enrMouvement.setNumeroCompte(line.substring(21,32));
                    enrMouvement.setCib(line.substring(32,34));
                    enrMouvement.setDateOperation(line.substring(34,40));
                    enrMouvement.setCodeMotifRejet(line.substring(40,42));
                    enrMouvement.setDateValeur(line.substring(42,48));
                    enrMouvement.setLibelle(line.substring(48,79));
                    enrMouvement.setZoneReservee2(line.substring(79,81));
                    enrMouvement.setNumEcriture(line.substring(81,88));
                    enrMouvement.setIndiceExoCommession(line.substring(88,89));
                    enrMouvement.setIndiceIndisponibilite(line.substring(89,90));
                    enrMouvement.setMontant(line.substring(90,104));
                    enrMouvement.setSens( sensMontant(line.substring(90,104)));
                    enrMouvement.setZoneReference(line.substring(104,120));

                    idEnrMouvement = enrMouvementRepository.save(enrMouvement).getId();
                }
                //Charger les lignes Complement (commence par 05))
                if(line.substring(0,2).equals("05")){
                    EnrComplement enrComplement = new EnrComplement();
                    EnrMouvement enrMouvement = new EnrMouvement();
                    enrMouvement.setId(idEnrMouvement);

                    enrComplement.setEnrMvt(enrMouvement);
                    enrComplement.setCodeEnregistrement("05");
                    enrComplement.setCodeBanque(line.substring(2,7));
                    enrComplement.setCoi(line.substring(7,11));
                    enrComplement.setCodeGuichet(line.substring(11,16));
                    enrComplement.setCodeDevise(line.substring(16,19));
                    enrComplement.setNbrDecimalesMontant(line.substring(19,20));
                    enrComplement.setZoneReservee1(line.substring(20,21));
                    enrComplement.setNumeroCompte(line.substring(21,32));
                    enrComplement.setCib(line.substring(32,34));
                    enrComplement.setDateOperation(line.substring(34,40));
                    enrComplement.setZoneReservee2(line.substring(40,45));
                    enrComplement.setQualifiantZone(line.substring(45,48));
                    enrComplement.setInfoComplementaire(line.substring(48,118));
                    enrComplement.setZoneReservee3(line.substring(118,120));

                    enrComplementRepository.save(enrComplement);
                }
            }

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Inserer les ligne Compta
    private void insererLigneCompta(Fichier fichier, String typeImportation, String nomFichier){
        try (Scanner scanner = new Scanner(new File(nomFichier))) {
            int charCode;
            String ligne = "";
            List<String> lignes = new ArrayList<String>();
            try(FileInputStream file = new FileInputStream(nomFichier)){
                InputStreamReader input = new InputStreamReader(file);
                //lire caractere par caractere
                while ((charCode = input.read()) != -1) {
                    //Test si un saut de ligne
                    if(charCode != 10){
                        ligne = ligne + ((char) charCode);
                    }
                    else{
                        lignes.add(ligne);
                        ligne = "";
                    }
                }
                input.close();
            }catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }

            for(String line: lignes){
                Comptabilite ligneCompta = new Comptabilite();
                ligneCompta.setCompteBancaire(line.substring(0,9));
                ligneCompta.setCodeFlux(line.substring(9,13));
                ligneCompta.setCodeBudgetaire(line.substring(13,17));
                ligneCompta.setDateComptable(line.substring(17,25));
                ligneCompta.setDateValeur(line.substring(27,35));
                ligneCompta.setMontant(line.substring(36,56));
                ligneCompta.setCode(line.substring(57,58));
                ligneCompta.setReference(line.substring(58,73));
                ligneCompta.setLibelle(line.substring(73,103));
                ligneCompta.setCompteComptable(line.substring(103,115));
                ligneCompta.setImputation(line.substring(115,127));
                ligneCompta.setLibelleComplementaire(line.substring(127, 131));
                ligneCompta.setCodeAnnulation(line.substring(138,139));
                ligneCompta.setNumeroPiece(line.substring(139,151));
                ligneCompta.setDevise(line.substring(295,298));
                ligneCompta.setSens(line.substring(298,299));
                ligneCompta.setFichier(fichier);

                comptabiliteRepository.save(ligneCompta);

            }
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<EnrMouvement> enrichirFichierBancaire(Integer idImportation) {

        logger.info("*** Debut enrichissement ***");
        String selectRequete = "";
        List<EnrMouvement> enrMouvementList = new ArrayList<EnrMouvement>();
        List<EnrMouvement> enrMouvementListAll = new ArrayList<EnrMouvement>();
        EnrMouvement enrMouvement = new EnrMouvement();

        //recuperer la banque a partir d une importation
        Importation importation = this.importationRepository.findById(idImportation).orElseThrow(()->new EntityNotFoundException(IMPORT_NOT_FOUND));
        Banque banque = importation.getBanque();

        //********************************  Changement CIB *********************************************
        //recuperer la liste des parametrages bancaires d'une banque
        List<ParametrageBancaire> paramBancaire = this.parametrageBancaireRepository.findParametrageBancaireByBanque(banque);
        logger.info("*** Debut enrichissement : Changement CIB ***");
        for(ParametrageBancaire p : paramBancaire){
            if(p.getActif()){
                selectRequete = "SELECT * FROM enr_mouvement "+
                        "WHERE enr_ancien_solde_id IN (SELECT id from enr_ancien_solde where fichier_id IN (SELECT id from fichier where importation_id ='"+idImportation+"')) "+
                        "AND cib = '" + p.getCib() + "' AND ";
                StringBuilder bld = new StringBuilder();
                bld.append(selectRequete);
                for(Condition c: p.getConditions()){
                    bld.append(" ");
                    bld.append(c.getConditionSQL());
                }
                selectRequete = bld.toString();

                //Recuperer les mouvements selon la condition
                enrMouvementList = this.importationRepository.selectMouvementCibUpdate(selectRequete);

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
        //********************************************************************************************************

        //********************** Ajout Enregistrement 05 contenant le code Gare **********************************
        logger.info("*** Debut enrichissement comptes gares ***");
        enrMouvementListAll.addAll(this.ajoutCodeGare(idImportation, banque));

        //********************************************************************************************************

        logger.info("*** Creation du fichier bancaire ***");
        this.creerFichierBancaireEnrichi(idImportation);

        //Vider les lignes 01-04-05-07
        logger.info("*** Vider les lignes 01-04-05-07 ***");
        List<EnrAncienSolde> enrAncienSoldes = enrAncienSoldeRepository.getAllLignesReleveByImporation(idImportation);
        this.enrAncienSoldeRepository.deleteAll(enrAncienSoldes);

        logger.info("*** Fin enrichissement ***");
        return enrMouvementListAll;
    }

    @Override
    public List<Comptabilite> enrichirFichierComptable(Integer idImportation) {
        logger.info("*** Debut enrichissement ***");
        String selectRequete = "";
        List<Comptabilite> lignesComptabilite = new ArrayList<Comptabilite>();
        Comptabilite ligneCompta = new Comptabilite();

        //********************************  Changement FLUX COMPTA *********************************************
        //recuperer la liste des parametrages comptables
        List<ParametrageComptable> parametrageComptables = parametrageComptableRepository.findAll();
        for(ParametrageComptable p : parametrageComptables){
            if(p.getActif()){
                selectRequete = "SELECT * FROM comptabilite "+
                        "WHERE fichier_id IN (SELECT id from fichier where importation_id ='"+idImportation+"') "+
                        "AND code_flux = '" + p.getFlux().getCodeFlux() + "' AND ";

                StringBuilder bld = new StringBuilder();
                bld.append(selectRequete);

                for(Condition c: p.getConditions()){
                    bld.append(" ");
                    bld.append(c.getConditionSQL());
                }
                selectRequete = bld.toString();

                //Recuperer les lignes compta selon la condition
                lignesComptabilite = this.importationRepository.selectLignesComptaFluxUpdate(selectRequete);

                //Parcourir les lignes compta pour mettre a jour le flux
                for(Comptabilite ligne : lignesComptabilite){
                    ligneCompta = comptabiliteRepository.findById(ligne.getId()).orElseThrow(()->new EntityNotFoundException("ligne compta non trouvé"));
                    ligneCompta.setId(ligne.getId());
                    ligneCompta.setNouveauFlux(p.getNouveauFlux().getCodeFlux());
                    comptabiliteRepository.save(ligneCompta);
                }

            }
        }

        logger.info("*** Creation du fichier comptable ***");
        this.creerFichierComptableEnrichi(idImportation);

        //Vider la table comptabilite
        logger.info("*** Vider la table comptabilite ***");
        List<Comptabilite> comptabilites = comptabiliteRepository.getAllLignesComptaByImportation(idImportation);
        this.comptabiliteRepository.deleteAll(comptabilites);

        logger.info("*** Fin enrichissement ***");

        return lignesComptabilite;
    }

    @Transactional
    private List<EnrMouvement> ajoutCodeGare(Integer idImportation, Banque banque){
        List<EnrMouvement> lignesMouvement = new ArrayList<EnrMouvement>();

        if(banque.getCodeBanque().equals("BMCE") || banque.getCodeBanque().equals("SG")){
            if(banque.getCodeBanque().equals("BMCE")){
                List<EnrMouvement> mouvements = enrMouvementRepository.getAllMouvementsByImportation(idImportation);
                for(EnrMouvement mvt : mouvements){
                    //Tester si le compte est un compte gare
                    CompteBancaire compteBancaire = compteBancaireRepository.findCompteBancaireByNumeroCompte(mvt.getNumeroCompte());
                    if(compteBancaire !=null){
                        EnrComplement complement = new EnrComplement();
                        complement.setCodeEnregistrement("05");
                        complement.setCodeBanque(mvt.getCodeBanque());
                        complement.setCoi(" ".repeat(4));
                        complement.setCodeGuichet(mvt.getCodeGuichet());
                        complement.setCodeDevise(mvt.getCodeDevise());
                        complement.setNbrDecimalesMontant(mvt.getNbrDecimalesMontant());
                        complement.setZoneReservee1(" ");
                        complement.setNumeroCompte(mvt.getNumeroCompte());
                        complement.setCib("91");
                        complement.setDateOperation(mvt.getDateOperation());
                        complement.setZoneReservee2(" ".repeat(5));
                        if(compteBancaire.isCentralisateur()){
                            complement.setQualifiantZone(mvt.getLibelle().substring(8,11));
                            complement.setInfoComplementaire((mvt.getLibelle().substring(11,12)).concat(" ".repeat(69)));
                        }
                        else{
                            complement.setQualifiantZone(compteBancaire.getCodeGare());
                            complement.setInfoComplementaire(" ".repeat(70));
                        }
                        complement.setZoneReservee3(" ".repeat(2));
                        complement.setEnrMvt(mvt);
                        enrComplementRepository.save(complement);
                        if (mvt !=null) lignesMouvement.add(mvt);
                    }
                }
            }
            //Banque SG
            else{
                List<EnrComplement> complements = enrComplementRepository.getAllComplementsByImportation(idImportation);
                EnrMouvement mouvement = new EnrMouvement();
                Integer mouvementId = 0;

                for(EnrComplement cmpl : complements){
                    String compteGare = "";
                    String codeGare = "";

                    mouvementId = enrComplementRepository.getIdMouvementByComplementId(cmpl.getId());
                    mouvement.setId(mouvementId);

                    CompteBancaire compteBancaire = compteBancaireRepository.findCompteBancaireByNumeroCompte(cmpl.getNumeroCompte().substring(2,9));
                    //Tester si le compte est un compte gare
                    if(compteBancaire !=null){
                        //Ce test permet de savoir si un mouvement a plus qu'un complement pour garantir la creation d'un seul complement 05
                        if(enrComplementRepository.getNbrEnrComplementByMouvement(mouvementId) < 3){
                            //recuperer le numero de compte de la gare a partir du champ Info Complementaire pour un compte centralisateur
                            //le numero de compte est les 7 derniers caracteres du champ Info Complementaire + 9 espaces
                            if(compteBancaire.isCentralisateur()) {
                                compteGare = cmpl.getInfoComplementaire().substring(cmpl.getInfoComplementaire().length()-16);
                                codeGare = compteBancaireRepository.findCompteBancaireByNumeroCompte(compteGare).getCodeGare();
                            }
                            //Ici le cas d'un sous compte
                            else codeGare = compteBancaire.getCodeGare();

                            EnrComplement complement = new EnrComplement();
                            complement.setCodeEnregistrement("05");
                            complement.setCodeBanque(cmpl.getCodeBanque());
                            complement.setCoi(" ".repeat(4));
                            complement.setCodeGuichet(cmpl.getCodeGuichet());
                            complement.setCodeDevise(cmpl.getCodeDevise());
                            complement.setNbrDecimalesMontant(cmpl.getNbrDecimalesMontant());
                            complement.setZoneReservee1(" ");
                            complement.setNumeroCompte(cmpl.getNumeroCompte());
                            complement.setCib(cmpl.getCib());
                            complement.setDateOperation(cmpl.getDateOperation());
                            complement.setZoneReservee2(" ".repeat(5));
                            complement.setQualifiantZone("REC");
                            complement.setInfoComplementaire(("ETTE DE LA GARE "+codeGare).concat(" ".repeat(52)));
                            complement.setZoneReservee3(" ".repeat(1));
                            complement.setEnrMvt(mouvement);
                            enrComplementRepository.save(complement);
                            if (complement.getEnrMvt() !=null) lignesMouvement.add(complement.getEnrMvt());
                        }
                    }
                }
            }
            return lignesMouvement;
        }
        else return lignesMouvement;
    }

    @Transactional
    private void creerFichierBancaireEnrichi(Integer idImportation){

        try {
            Importation importation = this.importationRepository.findById(idImportation).orElseThrow(()->new EntityNotFoundException(IMPORT_NOT_FOUND));
            Banque banque = importation.getBanque();
            Date date = new Date();
            String pathDownloadBank = DOWNLOAD_PATH;
            String outFileName = banque.getCodeBanque()+"_"+ date.getTime()/1000+"_"+importation.getId();

            // create a writer
            FileOutputStream fos = new FileOutputStream(pathDownloadBank + outFileName + ".txt");
            //ANSI ENCODAGE
            OutputStreamWriter writer = new OutputStreamWriter(fos, Charset.forName("Cp1252"));
            String cib = "";

            List<EnrAncienSolde> lignes = enrAncienSoldeRepository.getAllLignesReleveByImporation(idImportation);

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
                writer.write("\r\n");

                //Enregistrement 04
                for (EnrMouvement mvt : ligne.getEnregistrementMouvements()){
                    //Verifier si le CIB est modifie
                    if(mvt.getNouveauCib() == null) cib = mvt.getCib();
                    else cib = mvt.getNouveauCib();

                    writer.write(mvt.getCodeEnregistrement());
                    writer.write(mvt.getCodeBanque());
                    writer.write(mvt.getCoi());
                    writer.write(mvt.getCodeGuichet());
                    writer.write(mvt.getCodeDevise());
                    writer.write(mvt.getNbrDecimalesMontant());
                    writer.write(mvt.getZoneReservee1());
                    writer.write(mvt.getNumeroCompte());
                    writer.write(cib);
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
                    writer.write("\r\n");

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
                        writer.write(complement.getCib());
                        writer.write(complement.getDateOperation());
                        writer.write(complement.getZoneReservee2());
                        writer.write(complement.getQualifiantZone());
                        writer.write(complement.getInfoComplementaire());
                        writer.write(complement.getZoneReservee3());
                        writer.write("\r\n");
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
                writer.write("\r\n");
            }

            writer.close();
            importation.setOutFile(outFileName);
            importationRepository.save(importation);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Transactional
    private void creerFichierComptableEnrichi(Integer idImportation){
        try {
            Importation importation = this.importationRepository.findById(idImportation).orElseThrow(()->new EntityNotFoundException(IMPORT_NOT_FOUND));
            Date date = new Date();
            String pathDownloadCompta = DOWNLOAD_PATH;
            String outFileName = "TS_"+ date.getTime()/1000+idImportation;

            // create a writer
            FileOutputStream fos = new FileOutputStream(pathDownloadCompta + outFileName + ".txt");
            //ANSI ENCODAGE
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            String flux = "";

            List<Comptabilite> lignes = comptabiliteRepository.getAllLignesComptaByImportation(idImportation);
            for ( Comptabilite ligne : lignes){
                //Verifier si le CIB est modifie
                if(ligne.getNouveauFlux() == null) flux = ligne.getCodeFlux();
                else flux = ligne.getNouveauFlux();

                writer.write(ligne.getCompteBancaire());
                writer.write(flux);
                writer.write(ligne.getCodeBudgetaire());
                writer.write(ligne.getDateComptable());
                writer.write(" ".repeat(2));
                writer.write(ligne.getDateValeur());
                writer.write(" ");
                writer.write(ligne.getMontant());
                writer.write(" ");
                writer.write(ligne.getCode());
                writer.write(ligne.getReference());
                writer.write(ligne.getLibelle());
                writer.write(ligne.getCompteComptable());
                writer.write(ligne.getImputation());
                writer.write(ligne.getLibelleComplementaire());
                writer.write(" ".repeat(7));
                writer.write(ligne.getCodeAnnulation());
                writer.write(ligne.getNumeroPiece());
                writer.write(" ".repeat(144));
                writer.write(ligne.getDevise());
                writer.write(ligne.getSens());
                writer.write(" ");
                writer.write("\r\n");

            }
            writer.close();
            importation.setOutFile(outFileName);
            importationRepository.save(importation);

        }catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public String telechargerFichier(Integer idImportation){
        Importation importation = this.importationRepository.findById(idImportation)
                .orElseThrow(()->new EntityNotFoundException(IMPORT_NOT_FOUND));
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
