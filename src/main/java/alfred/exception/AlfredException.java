package alfred.exception;

/**
 * Represents an exception specific to Alfred.
 */


public class AlfredException extends Exception {
    /**
     * Creates a new AlfredException with the given message.
     *
     * @param message The error message.
     */

    public AlfredException(String message) {
        super(message);
    }
}
