package by.newsportal.news.servise.exception;

public class ServiseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1869044957362476023L;

	public ServiseException() {
		super();
	}

	public ServiseException(String message) {
		super(message);
	}

	public ServiseException(Exception e) {
		super(e);
	}

	public ServiseException(String message, Exception e) {
		super(message, e);
	}
}
