package bg.uni_sofia.fmi.oopjava.project.exceptions;

/**
 *
 * @author Dimitar Panayotov
 */
public class NoPermissionException extends Exception {

    public NoPermissionException() {
        this("You don't have permission to perform this action!");
    }

    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionException(Throwable cause) {
        super(cause);
    }

    public NoPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
