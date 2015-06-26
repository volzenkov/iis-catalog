package khpi.iip.mipk.kharkiv.edu.dao.exceptions;

/**
 * @author: vzenkov
 */
public class RemoveException extends Exception {

    public RemoveException() {
    }

    public RemoveException(String message) {
        super(message);
    }

    public RemoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoveException(Throwable cause) {
        super(cause);
    }
}
