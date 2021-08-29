package by.newsportal.news.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1869044957362476023L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}
}
