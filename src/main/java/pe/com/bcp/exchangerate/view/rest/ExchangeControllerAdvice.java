package pe.com.bcp.exchangerate.view.rest;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import pe.com.bcp.exchangerate.view.dto.response.BaseWebResponse;
import pe.com.bcp.exchangerate.view.dto.response.ErrorCode;

@Slf4j
@RestControllerAdvice(basePackages = { "pe.com.bcp.exchangerate.view.rest" })
public class ExchangeControllerAdvice {
	
	
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleEntityNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseWebResponse.error(ErrorCode.ENTITY_NOT_FOUND));
    }
    
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<BaseWebResponse> handleEntityExistsException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseWebResponse.error(ErrorCode.ENTITY_EXIST));
    }
}
