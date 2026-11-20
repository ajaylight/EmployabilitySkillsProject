package exceptions;

/**
 * Thrown when the requested booking ID cannot be found.
 */
public class BookingNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookingNotFoundException(String message) {
        super(message);
    }
}
