package ma.oncf.sfa.moulinette.dto;

import lombok.Data;
import ma.oncf.sfa.moulinette.entities.Importation;

import java.util.List;

@Data
public class BanqueResDto {
    private Integer id;
    private String codeBanque;
    private String nomBanque;
    private List<Importation> importations;
}
