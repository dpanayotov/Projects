package bg.uni_sofia.fmi.oopjava.project.exceptions;

/**
 *
 * @author Dimitar Panayotov
 */
public class InvalidCardNumberException extends Exception {

    public InvalidCardNumberException() {
        this("Invalid card number!");
    }

    public InvalidCardNumberException(String message) {
        super(message);
    }

    public InvalidCardNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCardNumberException(Throwable cause) {
        super(cause);
    }

    public InvalidCardNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }   
}
