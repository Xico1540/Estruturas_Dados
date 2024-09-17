/**
 * Exception thrown when attempting an operation on an empty collection.
 */
package exceptions;

public class EmptyCollectionException extends Exception {

    /**
     * Constructs a new EmptyCollectionException with no specified detail message.
     */
    public EmptyCollectionException() {

    }

    /**
     * Constructs a new EmptyCollectionException with the specified detail message.
     *
     * @param message The detail message.
     */
    public EmptyCollectionException(String message) {
        super(message);
    }
}
