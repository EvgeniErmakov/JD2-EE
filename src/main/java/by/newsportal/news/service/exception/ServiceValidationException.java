package by.newsportal.news.service.exception;

public class ServiceValidationException extends Exception {

	private static final long serialVersionUID = -6068030031725584985L;

	public ServiceValidationException() {
		super();
	}

	public ServiceValidationException(String message) {
		super(message);
	}

	public ServiceValidationException(Exception e) {
		super(e);
	}

	public ServiceValidationException(String message, Exception e) {
		super(message, e);
	}
	
	
}
