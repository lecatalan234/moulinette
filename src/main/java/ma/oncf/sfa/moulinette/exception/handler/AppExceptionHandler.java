package ma.oncf.sfa.moulinette.exception.handler;

import ma.oncf.sfa.moulinette.exception.EntityNotFoundException;
import ma.oncf.sfa.moulinette.exception.ErrorMEssage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException e){
        ErrorMEssage errorMessage = new ErrorMEssage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setTimestamp(new Date());
        errorMessage.setCode(404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
