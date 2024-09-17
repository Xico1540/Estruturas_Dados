package implementation_classes;

import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;

import java.util.Iterator;

public interface GraphADT<T> {
    /**
     * Adds a vertex to this graph, associating object with vertex.
     *
     * @param vertex the vertex to be added to this graph.
     */
    void addVertex(T vertex);

    /**
     * Removes a single vertex with the given value from this graph.
     * @param vertex the vertex to be removed form this graph.
     */
    void removeVertex(T vertex) throws EmptyCollectionException, ElementNotFoundException;

    /**
     * Inserts an edge between two vertices of this graph.
     *
     * @param firstVertex the first vertex.
     * @param secondVertex the second vertex.
     */
    void addEdge(T firstVertex, T secondVertex) throws ElementNotFoundException;

    /**
     * Removes an edge between two vertices of this graph.
     *
     * @param firstVertex the first vertex.
     * @param secondVertex the second vertex.
     */
    void removeEdge(T firstVertex, T secondVertex) throws ElementNotFoundException;

    /**
     * Returns a breadth first iterator starting with the given vertex.
     * @param startVertex the starting vertex.
     * @return            a breadth first iterator beginning at the given vertex.
     */
    Iterator iteratorBFS(T startVertex) throws ElementNotFoundException, EmptyCollectionException;

    /**
     * Returns a depth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex.
     * @return            a depth first iterator starting at the given vertex.
     */
    Iterator iteratorDFS(T startVertex) throws ElementNotFoundException, EmptyCollectionException;

    /**
     *  Returns an iterator that contains the shortest path between the two vertices.
     * @param startVertex the starting vertex.
     * @param targetVertex the ending vertex.
     * @return              an iterator that contains the shortest path between the two vertices.
     */
    Iterator iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException, EmptyCollectionException;

    /**
     * Returns true if this graph is empty, false otherwise.
     *
     * @return true if this graph is empty.
     */
    boolean isEmpty();

    /**
     * Returns true if this graph is connected, false otherwise.
     *
     * @return true if this graph is connected.
     */
    boolean isConnected();

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the integer number of vertices in this graph.
     */
    int size();

    /**
     * Returns a string representation of the adjacency matrix.
     *
     * @return a string representation of the adjacency matrix.
     */
    String toString();
}
