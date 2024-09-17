package implementation_classes;

import exceptions.EmptyCollectionException;
import implementation_classes.Nodes.LinearNode;

/**
 * LinkedStack represents a linked implementation of a stack.
 *
 * @param <T> The type of elements stored in the stack.
 */
public class LinkedStack<T> implements StackADT<T> {
    private LinearNode<T> top; // Top node of the stack
    private int count; // Number of elements in the stack

    /**
     * Creates an empty LinkedStack.
     */
    public LinkedStack() {
        top = null;
        count = 0;
    }

    /**
     * Adds the specified element to the top of the stack.
     *
     * @param element The element to be pushed onto the stack.
     */
    public void push(T element) {
        LinearNode<T> node = new LinearNode<>(element);
        if (this.top == null) {
            top = node;
        } else {
            node.setNext(top);
            top = node;
        }

        count++;
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return The element removed from the top of the stack.
     * @throws EmptyCollectionException If the stack is empty.
     */
    public T pop() throws EmptyCollectionException {
        if (top == null) {
            throw new EmptyCollectionException("Stack");
        }
        T data = top.getCurrent();
        top = top.getNext();

        count--;
        return data;
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return The element at the top of the stack.
     * @throws EmptyCollectionException If the stack is empty.
     */
    public T peek() throws EmptyCollectionException {
        if (top == null) {
            throw new EmptyCollectionException("Stack");
        }

        T data = top.getCurrent();
        return data;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return True if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (this.top == null);
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return The number of elements in the stack.
     */
    public int size() {
        return this.count;
    }

    /**
     * Returns a string representation of the elements in the stack.
     *
     * @return A string representation of the elements in the stack.
     */
    @Override
    public String toString() {
        String s = "Linked Stack Elements\n";
        LinearNode<T> node = top.getNext();
        for (int i = 0; i < count && top.getNext() != null; i++) {
            s += node.toString();
        }

        return s;
    }
}
