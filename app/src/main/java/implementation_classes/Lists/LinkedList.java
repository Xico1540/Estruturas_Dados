package implementation_classes.Lists;
import exceptions.EmptyCollectionException;
import implementation_classes.Nodes.LinearNode;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements ListADT<T> {
    protected LinearNode<T> front;
    protected LinearNode<T> rear;
    protected int count;
    protected int modCount;
    public LinkedList() {
        front = null;
        rear = null;
        count = 0;
        modCount = 0;
    }
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Linked List");

        T removedElement = front.getCurrent();
        front = front.getNext();
        count--;
        modCount++;
        return removedElement;
    }


    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Linked List");

        T removedElement;
        if (size() == 1) {
            removedElement = front.getCurrent();
            front = rear = null;
        } else {
            LinearNode<T> traverse = front;
            for (int i = 0; i < size() - 2; i++) {
                traverse = traverse.getNext();
            }
            removedElement = traverse.getNext().getCurrent();
            traverse.setNext(null);
            rear = traverse;
        }

        count--;
        modCount++;
        return removedElement;
    }


    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Linked List");

        LinearNode<T> traverse = front;
        T removedElement = null;

        if (front.getCurrent().equals(element)) {
            removedElement = front.getCurrent();
            front = front.getNext();
            count--;
            modCount++;
            return removedElement;
        }

        for (int i = 0; i < size() && traverse.getNext() != null; i++) {
            if (traverse.getNext().getCurrent().equals(element)) {
                LinearNode<T> newNext = traverse.getNext().getNext();
                removedElement = traverse.getNext().getCurrent();

                if (traverse.getNext() == rear) {
                    rear = traverse;
                }

                traverse.setNext(newNext);
                count--;
                modCount++;
                break;
            }
            traverse = traverse.getNext();
        }

        return removedElement;
    }


    @Override
    public T first() {
        return front.getCurrent();
    }

    @Override
    public T last() {
        return rear.getCurrent();
    }

    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Linked List");
        LinearNode<T> traverse = rear;
        for(int i = 0; i < count && traverse != null; i++) {
            if (traverse.getCurrent().equals(target)) {
                return true;
            }
            traverse = traverse.getNext();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(modCount);
    }

    private class LinkedListIterator<T> implements Iterator<T> {
        private LinearNode<T> current;
        private final int expectedModCount;
        private int iteratorCounter;
        public LinkedListIterator(int modCount) {
            current = (LinearNode<T>) front;
            expectedModCount = modCount;
            iteratorCounter = 0;
        }
        @Override
        public boolean hasNext() {
            return (iteratorCounter < count);
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("Linked List");

            iteratorCounter++;
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException("Linked List: Mod Counter");
            T result = current.getCurrent();
            current = current.getNext();
            return result;
        }
    }

    @Override
    public String toString() {
        LinearNode<T> traverse = front;
        String s = "";
        for(int i = 0; i < size() && traverse != null; i++) {
            if(traverse == front) {
                s += "#FRONT#";
            }else if (traverse == rear) {
                s += "#REAR#";
            }
            s += "[&" + traverse + "\t*" +traverse.getCurrent() + "\t->\t" + traverse.getNext() + "]\n";
            traverse = traverse.getNext();
        }
        return s;
    }
}
