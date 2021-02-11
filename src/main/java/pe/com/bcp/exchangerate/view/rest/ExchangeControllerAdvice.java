package pe.com.bcp.exchangerate.view.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import pe.com.bcp.exchangerate.application.exceptions.ApplicationUserNotFound;
import pe.com.bcp.exchangerate.application.exceptions.BadCredentialsException;
import pe.com.bcp.exchangerate.application.exceptions.ExchangeRateExistException;
import pe.com.bcp.exchangerate.application.exceptions.ExchangeRateNotFoundException;
import pe.com.bcp.exchangerate.view.dto.response.BaseWebResponse;
import pe.com.bcp.exchangerate.view.dto.response.ErrorCode;

@RestControllerAdvice(basePackages = { "pe.com.bcp.exchangerate.view.rest" })
public class ExchangeControllerAdvice {

	@ExceptionHandler(ExchangeRateExistException.class)
	public ResponseEntity<BaseWebResponse> handleEntityNotFoundException(ExchangeRateExistException e) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(BaseWebResponse.error(ErrorCode.EXCHANGERATE_EXIST, e.getMessage()));
	}

	@ExceptionHandler(ExchangeRateNotFoundException.class)
	public ResponseEntity<BaseWebResponse> handleEntityExistsException(ExchangeRateNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(BaseWebResponse.error(ErrorCode.EXCHANGERATE_NOT_FOUND, e.getMessage()));
	}

	@ExceptionHandler(ApplicationUserNotFound.class)
	public ResponseEntity<BaseWebResponse> handleApplicationUserNotFound(ApplicationUserNotFound e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(BaseWebResponse.error(ErrorCode.CREDENTIALS_INVALID, e.getMessage()));
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<BaseWebResponse> handleBadCredentialsException(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(BaseWebResponse.error(ErrorCode.CREDENTIALS_INVALID, e.getMessage()));
	}
}
