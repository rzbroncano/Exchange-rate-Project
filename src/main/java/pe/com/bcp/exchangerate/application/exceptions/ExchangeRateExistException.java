package pe.com.bcp.exchangerate.application.exceptions;

public class ExchangeRateExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExchangeRateExistException(String message) {
		super(message);
	}
}
