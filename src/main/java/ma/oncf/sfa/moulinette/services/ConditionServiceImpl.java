package ma.oncf.sfa.moulinette.services;

import ma.oncf.sfa.moulinette.dto.ConditionReqDto;
import ma.oncf.sfa.moulinette.dto.ConditionResDto;
import ma.oncf.sfa.moulinette.entities.Condition;
import ma.oncf.sfa.moulinette.entities.ParametrageBancaire;
import ma.oncf.sfa.moulinette.mapper.ConditionMapper;
import ma.oncf.sfa.moulinette.mapper.ParametrageBancaireMapper;
import ma.oncf.sfa.moulinette.repositories.ConditionRepository;
import ma.oncf.sfa.moulinette.repositories.ParametrageBancaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConditionServiceImpl implements ConditionService {
    @Autowired
    private ConditionRepository conditionRepository;
    @Autowired
    private ConditionMapper conditionMapper;
    @Autowired
    private ParametrageBancaireRepository paramBancaireRepository;
    @Autowired
    private ParametrageBancaireMapper paramBancaireMapper;
    @Autowired
    private ParametrageBancaireService parametrageBancaireService;

    @Override
    public List<ConditionResDto> getAllConditions() {
        List<Condition> conditions = conditionRepository.findAll();
        return conditions.stream().map(condition -> conditionMapper.conditionToConditionDto(condition))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConditionResDto save(ConditionReqDto conditionReqDto) {
        String op= "";
        String opLogique= "";
        String valeur= "";
        String champ = "";
        String conditionSQL;

        Condition condition = conditionMapper.conditionDtoToCondition(conditionReqDto);

        //Traduire les operateurs + valeurs
        if(conditionReqDto.getOperateur().equals("égal à")) {
            op = "=";
            valeur = "'" + conditionReqDto.getValeur()+ "'";
        }
        else if(conditionReqDto.getOperateur().equals("différent de")) {
            op = "<>";
            valeur = "'" + conditionReqDto.getValeur()+ "'";
        }
        else if(conditionReqDto.getOperateur().equals("contient")) {
            op = "LIKE";
            valeur = "'%" + conditionReqDto.getValeur()+ "%'";
        }
        else{
            op = "NOT LIKE";
            valeur = "'%" + conditionReqDto.getValeur()+ "%'";
        }

        //Traduire les operateurs logique
        if(conditionReqDto.getOperateurLogique() !=null){
            if(conditionReqDto.getOperateurLogique().equals("ET")) {opLogique = "AND ";}
            else opLogique = "OR ";
        }

        //Traduire les champs
        if(conditionReqDto.getChamp().equals("Libellé")) champ = "libelle";
        else if(conditionReqDto.getChamp().equals("Code opération")) champ = "coi";
        else if(conditionReqDto.getChamp().equals("Sens")) champ = "sens";
        else if(conditionReqDto.getChamp().equals("Numéro de compte")) champ = "numero_compte";
        else if(conditionReqDto.getChamp().equals("Info 1")) champ = "info_complementaire";
        else champ = "info_complementaire";

        //Ajouter la requete sous format SQL
        conditionSQL = opLogique + champ + " " + op  + " " + valeur;
        condition.setConditionSQL(conditionSQL);

        return conditionMapper.conditionToConditionDto(conditionRepository.save(condition));
    }

    @Override
    public ConditionResDto update(ConditionReqDto conditionReqDto, Integer id) {
        conditionReqDto.setId(id);
        Condition condition = conditionMapper.conditionDtoToCondition(conditionReqDto);
        return conditionMapper.conditionToConditionDto(conditionRepository.save(condition));
    }

    @Override
    public void deleteById(Integer id) {
        conditionRepository.deleteById(id);
    }

    @Override
    public List<ConditionResDto> getConditionByParamBancaire(Integer idParam) {
        ParametrageBancaire parametrageBancaire = new ParametrageBancaire();
        parametrageBancaire.setId(idParam);
        List<Condition> conditions = conditionRepository.findConditionByParametrageBancaire(parametrageBancaire);
        return conditions.stream().map(condition -> conditionMapper.conditionToConditionDto(condition))
                .collect(Collectors.toList());
    }
}
