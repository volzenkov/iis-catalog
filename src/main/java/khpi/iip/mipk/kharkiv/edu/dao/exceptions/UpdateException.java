package khpi.iip.mipk.kharkiv.edu.dao.exceptions;

/**
 * @author: vzenkov
 */
public class UpdateException extends Exception {

    public UpdateException() {
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }
}
