package insurance.management.config;

import insurance.management.config.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> BusinessExceptionHandler(BusinessException ex) {
        return new ResponseEntity<>(makeBody(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }
    private static Map<String,String> makeBody(String message){
        Map<String,String> body =  new HashMap<>();
        body.put("result",message);
        return body;
    }

}
