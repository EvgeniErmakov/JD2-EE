package by.newsportal.news.servise.exception;

public class ServiseValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6068030031725584985L;

	public ServiseValidationException() {
		super();
	}

	public ServiseValidationException(String message) {
		super(message);
	}

	public ServiseValidationException(Exception e) {
		super(e);
	}

	public ServiseValidationException(String message, Exception e) {
		super(message, e);
	}
	
	
}
