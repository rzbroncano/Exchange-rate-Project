package pe.com.bcp.exchangerate.application.exceptions;

public class ApplicationUserNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ApplicationUserNotFound(String message) {
		super(message);
	}
}
