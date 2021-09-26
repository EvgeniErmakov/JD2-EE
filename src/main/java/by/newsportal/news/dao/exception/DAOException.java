package by.newsportal.news.dao.exception;

public class DAOException extends Exception {
    private static final long serialVersionUID = 4383986880695348976L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
