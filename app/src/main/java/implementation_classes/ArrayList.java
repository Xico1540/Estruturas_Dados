/**
 * Abstract class representing an array-based implementation of a list.
 *
 * @param <T> The type of elements stored in the list.
 */
package implementation_classes;

import implementation_classes.Lists.ListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class ArrayList<T> implements ListADT<T> {
    private static final int DEFAULT_CAPACITY = 100;
    private static final int NOT_FOUND = -1;
    private static final int EXPAND_BY = 2;
    protected int rear;
    protected int modCount;
    protected T[] list;

    /**
     * Constructs a new ArrayList with the default capacity.
     */
    public ArrayList() {
        this.rear = 0;
        this.modCount = 0;
        this.list = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Removes and returns the first element from the list.
     *
     * @return The first element removed from the list, or null if the list is empty.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T element = list[0];

        for(int i = 0; i < rear; i++) {
            list[i] = list[i + 1];
        }

        list[--rear] = null;
        modCount++;
        return element;
    }

    /**
     * Removes and returns the last element from the list.
     *
     * @return The last element removed from the list, or null if the list is empty.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T element = list[rear - 1];

        list[--rear] = null;
        modCount++;
        return element;
    }

    /**
     * Finds the index of the specified element in the list.
     *
     * @param element The element to find.
     * @return The index of the element, or -1 if not found.
     */
    private int findIndex(T element) {
        for(int i = 0; i < rear; i++)
            if(list[i].equals(element))
                return i;

        return NOT_FOUND;
    }

    /**
     * Removes the specified element from the list.
     *
     * @param element The element to remove.
     * @return The removed element, or null if the element is not found.
     */
    public T remove(T element) {
        if(isEmpty()) {
            return null;
        }

        int elementIndex = findIndex(element);

        if(elementIndex == NOT_FOUND) {
            return null;
        }

        T removedElement = list[elementIndex];

        for(int i = elementIndex; i < rear; i++) {
            list[i] = list[i + 1];
        }

        list[--rear] = null;
        modCount++;
        return removedElement;
    }

    /**
     * Returns the first element in the list.
     *
     * @return The first element in the list, or null if the list is empty.
     */
    public T first() {
        if(isEmpty()) {
            return null;
        }

        return list[0];
    }

    /**
     * Returns the last element in the list.
     *
     * @return The last element in the list, or null if the list is empty.
     */
    public T last() {
        if(isEmpty()) {
            return null;
        }

        return list[rear - 1];
    }

    /**
     * Checks if the list contains the specified element.
     *
     * @param target The element to check for.
     * @return true if the element is found, false otherwise.
     */
    public boolean contains(T target) {
        return (findIndex(target) != NOT_FOUND);
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (rear == 0);
    }

    /**
     * Returns the size of the list.
     *
     * @return The size of the list.
     */
    public int size() {
        return rear;
    }

    /**
     * Iterator implementation for the ArrayList.
     */
    private class ArrayIterator implements Iterator<T> {
        private final T[] array;
        private int i;
        private final int expectedModCount;

        /**
         * Constructs a new ArrayIterator.
         *
         * @param modCount The current modification count of the list.
         */
        public ArrayIterator(int modCount) {
            array = list;
            i = 0;
            expectedModCount = modCount;
        }

        /**
         * Checks if there is a next element in the list.
         *
         * @return true if there is a next element, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return i < rear;
        }

        /**
         * Returns the next element in the list.
         *
         * @return The next element.
         * @throws NoSuchElementException if there is no next element.
         * @throws RuntimeException if the modification count has changed during iteration.
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if(expectedModCount != modCount) {
                throw new RuntimeException();
            }
            return array[i++];
        }

        /**
         * Unsupported operation. Removal is not supported.
         *
         * @throws UnsupportedOperationException always.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns an iterator for the list.
     *
     * @return An iterator for the list.
     */
    public Iterator<T> iterator() {
        return new ArrayIterator(modCount);
    }

    /**
     * Expands the capacity of the list.
     */
    public void expandCapacity() {
        T[] larger = (T[]) new Object[this.rear * EXPAND_BY];

        if (rear >= 0) System.arraycopy(this.list, 0, larger, 0, rear);

        this.list = larger;
    }

    /**
     * Returns a string representation of the list.
     *
     * @return A string representation of the list.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < rear; i++) {
            s.append(list[i]);
        }
        return s.toString();
    }
}
