package exceptions;

/**
 * Thrown when a user enters a show ID that does not exist.
 */
public class InvalidShowException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidShowException(String message) {
        super(message);
    }
}
