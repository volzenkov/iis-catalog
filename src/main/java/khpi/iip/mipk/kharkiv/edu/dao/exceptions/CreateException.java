package khpi.iip.mipk.kharkiv.edu.dao.exceptions;

/**
 * @author: vzenkov
 */
public class CreateException extends Exception {

    public CreateException() {
    }

    public CreateException(String message) {
        super(message);
    }

    public CreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateException(Throwable cause) {
        super(cause);
    }
}
