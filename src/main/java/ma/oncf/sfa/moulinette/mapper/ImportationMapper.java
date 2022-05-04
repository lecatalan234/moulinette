package ma.oncf.sfa.moulinette.mapper;

import ma.oncf.sfa.moulinette.dto.ImportationReqDto;
import ma.oncf.sfa.moulinette.dto.ImportationResDto;
import ma.oncf.sfa.moulinette.entities.Importation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImportationMapper {
    Importation importationDtoToImportation(ImportationReqDto importationReqDto);
    ImportationResDto importationToImportationDto(Importation importation);
}
