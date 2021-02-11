package pe.com.bcp.exchangerate.application.exceptions;

public class ExchangeRateNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExchangeRateNotFoundException(String message) {
		super(message);
	}
}
