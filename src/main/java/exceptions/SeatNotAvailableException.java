package exceptions;

/**
 * Thrown when a customer tries to book a seat that is not available.
 */
public class SeatNotAvailableException extends Exception {

    private static final long serialVersionUID = 1L;

    public SeatNotAvailableException(String message) {
        super(message);
    }
}
