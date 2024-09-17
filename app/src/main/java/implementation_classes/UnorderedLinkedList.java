package implementation_classes;

import exceptions.EmptyCollectionException;
import implementation_classes.Lists.LinkedList;
import implementation_classes.Lists.UnorderedListADT;
import implementation_classes.Nodes.LinearNode;

import java.util.NoSuchElementException;

public class UnorderedLinkedList<T> extends LinkedList<T> implements UnorderedListADT<T> {

    @Override
    public void addToFront(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        }else {
            newNode.setNext(front);
            front = newNode;
        }
        count++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            LinearNode<T> traverse = front;
            while (traverse.getNext() != null) {
                traverse = traverse.getNext();
            }
            traverse.setNext(newNode);
            rear = newNode; // Update the rear to the new node
        }
        count++;
        modCount++;
    }


    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Linked List");

        LinearNode<T> traverse = front;
        LinearNode<T> newNode = new LinearNode<>(element);
        boolean found = false;

        while (traverse != null) {
            if (traverse.getCurrent().equals(target)) {
                LinearNode<T> next = traverse.getNext();
                traverse.setNext(newNode);
                newNode.setNext(next);
                found = true;
                break; // Break out of the loop once the target is found
            }
            traverse = traverse.getNext();
        }

        if (!found)
            throw new NoSuchElementException("Linked List");

        count++;
        modCount++;
    }

}
