/**
 * Represents a node in a linear data structure.
 *
 * @param <T> The type of the data stored in the node.
 */
package implementation_classes.Nodes;

public class LinearNode<T> {
    private T current;
    private LinearNode<T> next;

    /**
     * Constructs a new LinearNode with the specified data.
     *
     * @param current The data to be stored in the node.
     */
    public LinearNode(T current) {
        this.current = current;
    }

    /**
     * Constructs a new empty LinearNode with no data.
     */
    public LinearNode() {
        this.current = null;
        this.next = null;
    }

    /**
     * Retrieves the data stored in the node.
     *
     * @return The data stored in the node.
     */
    public T getCurrent() {
        return current;
    }

    /**
     * Sets the data to be stored in the node.
     *
     * @param current The data to be stored in the node.
     */
    public void setCurrent(T current) {
        this.current = current;
    }

    /**
     * Retrieves the next node in the linear structure.
     *
     * @return The next node in the linear structure.
     */
    public LinearNode<T> getNext() {
        return next;
    }

    /**
     * Sets the next node in the linear structure.
     *
     * @param next The next node in the linear structure.
     */
    public void setNext(LinearNode<T> next) {
        this.next = next;
    }
}
