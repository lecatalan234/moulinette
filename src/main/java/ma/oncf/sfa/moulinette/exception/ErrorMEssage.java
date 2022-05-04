package ma.oncf.sfa.moulinette.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMEssage {
    private String message;
    private Date timestamp;
    private Integer code;
}
