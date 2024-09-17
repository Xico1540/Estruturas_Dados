/**
 * Exception thrown when an element is not found.
 */
package exceptions;

public class ElementNotFoundException extends Exception {

    /**
     * Constructs a new ElementNotFoundException with no specified detail message.
     */
    public ElementNotFoundException() {
    }

    /**
     * Constructs a new ElementNotFoundException with the specified detail message.
     *
     * @param message The detail message.
     */
    public ElementNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ElementNotFoundException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ElementNotFoundException with the specified cause and no detail message.
     *
     * @param cause The cause of the exception.
     */
    public ElementNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ElementNotFoundException with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            The detail message.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether or not suppression is enabled or disabled.
     * @param writableStackTrace Whether or not the stack trace should be writable.
     */
    public ElementNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
