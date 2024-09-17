package implementation_classes;

import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import implementation_classes.Heaps.PriorityQueue;
import implementation_classes.Queues.LinkedQueue;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {
    private static final int EXPAND_BY = 2;
    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph.
    protected boolean[][] adjMatrix; // matrix adjacency
    protected T[] vertices; // values of vertices

    public Graph() {
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Inserts an edge between two vertices in the graph.
     * @param vertex the vertex to be added to this graph.
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length)
            expandCapacity();
        vertices[numVertices] = (T) vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    /**
     * Expands the capacity of the graph.
     */
    protected void expandCapacity() {

        T[] temporaryArray = vertices;
        vertices = (T[])(new Object[vertices.length * EXPAND_BY]);

        for (int i = 0; i < temporaryArray.length; i++)
            vertices[i] = temporaryArray[i];

        boolean[][] temporaryAdjMatrix = adjMatrix;
        adjMatrix = new boolean[adjMatrix.length * EXPAND_BY][adjMatrix.length * EXPAND_BY];

        for(int i = 0; i < temporaryAdjMatrix.length; i++)
            for (int j = 0; j < temporaryAdjMatrix.length; j++)
                adjMatrix[i][j] = temporaryAdjMatrix[i][j];
    }
    @Override
    public void removeVertex(T vertex) throws EmptyCollectionException, ElementNotFoundException {
        if (numVertices == 0)
            throw new EmptyCollectionException("Empty Graph");

        int index = getIndex(vertex);
        if (indexIsValid(index)) {
            // Remove the vertex from the vertices array
            for (int i = index; i < numVertices - 1; i++) {
                vertices[i] = vertices[i + 1];
            }
            vertices[numVertices - 1] = null;

            // Adjust the adjacency matrix
            for (int i = index; i < numVertices - 1; i++) {
                for (int j = 0; j < numVertices - 1; j++) {
                    adjMatrix[i][j] = adjMatrix[i + 1][j];
                }
            }

            // Resize the adjacency matrix
            boolean[][] newAdjMatrix = new boolean[numVertices - 1][numVertices - 1];
            for (int i = 0; i < numVertices - 1; i++) {
                for (int j = 0; j < numVertices - 1; j++) {
                    newAdjMatrix[i][j] = adjMatrix[i][j];
                }
            }
            adjMatrix = newAdjMatrix;

            numVertices--;
        } else {
            throw new ElementNotFoundException("Vertex not found");
        }
    }

    @Override
    public void addEdge(T firstVertex, T secondVertex) throws ElementNotFoundException {
        try {
            addEdge(getIndex(firstVertex), getIndex(secondVertex));
        } catch(ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
    }


    private void addEdge(int firstIndex, int secondIndex) {
        if (indexIsValid(firstIndex) && indexIsValid(secondIndex)) {
            adjMatrix[firstIndex][secondIndex] = true;
        }
    }

    @Override
    public void removeEdge(T firstVertex, T secondVertex) throws ElementNotFoundException {
        try {
            removeEdge(getIndex(firstVertex), getIndex(secondVertex));
        } catch(ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
    }

    private void removeEdge(int firstIndex, int secondIndex) {
        if (indexIsValid(firstIndex) && indexIsValid(secondIndex)) {
            adjMatrix[firstIndex][secondIndex] = false;
        }
    }
    public int getIndex(T vertex) throws ElementNotFoundException {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null)
                if (vertices[i].equals(vertex))
                    return i;
        }
        throw new ElementNotFoundException("Could not find vertex");
    }
    protected boolean indexIsValid(int index) {
        return (index < numVertices && index >= 0);
    }

    /**
     * Returns an iterator that performs a breadth first search
     * traversal starting at the given index
     *
     * @param startVertex the starting vertex.
     * @return an iterator that performs a breadth first traversal
     * @throws EmptyCollectionException thrown when an error occurs while in dequeue
     * @throws ElementNotFoundException thrown when the start vertex is not found.
     */
    @Override
    public Iterator iteratorBFS(T startVertex) throws EmptyCollectionException, ElementNotFoundException  {
        return iteratorBFS(getIndex(startVertex));
    }

    private Iterator<T> iteratorBFS(int startIndex) throws EmptyCollectionException {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex))
            return resultList.iterator();

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;
        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while(!traversalQueue.isEmpty()) {
                x = traversalQueue.dequeue();
                resultList.addToRear(vertices[x.intValue()]);
                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[x.intValue()][i] && !visited[i]) {
                        traversalQueue.enqueue(i);
                        visited[i] = true;
                    }
                }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that performs a depth first search
     * traversal starting at the given index.
     *
     * @param startVertex the starting vertex.
     * @return            an iterator that performs a depth first traversal.
     * @throws EmptyCollectionException thrown when an error occurs while in dequeue
     * @throws ElementNotFoundException thrown when the start vertex is not found.
     */
    @Override
    public Iterator iteratorDFS(T startVertex) throws ElementNotFoundException, EmptyCollectionException {
        return iteratorDFS(getIndex(startVertex));
    }

    private Iterator<T> iteratorDFS(int startIndex) throws EmptyCollectionException, ElementNotFoundException {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        if(!indexIsValid(startIndex))
            return resultList.iterator();

        for(int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;
        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty())
                traversalStack.pop();
        }

        // Ensure the last vertex is added to the resultList
        if (!traversalStack.isEmpty()) {
            resultList.addToRear(vertices[traversalStack.pop()]);
        }        return resultList.iterator();
    }

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException, EmptyCollectionException {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    private Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) throws ElementNotFoundException, EmptyCollectionException {
        ArrayUnorderedList<T> result = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            throw new IllegalArgumentException("Invalid start or target vertex");
        }

        int[] pred = new int[numVertices];
        double[] D = new double[numVertices];
        D[startIndex] = 0;

        for (int i = 0; i < numVertices; i++) {
            if (i != startIndex) {
                D[i] = Double.POSITIVE_INFINITY;
            }
            pred[i] = -1;
        }

        PriorityQueue<T> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < numVertices; i++) {
            priorityQueue.addElement(vertices[i], (int) D[i]);
        }

        while (!priorityQueue.isEmpty()) {
            T u = priorityQueue.removeNext();
            int uIndex = getIndex(u);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[uIndex][i]) {
                    double weight = 1.0;
                    if (D[uIndex] + weight < D[i]) {
                        T element = vertices[i];
                        D[i] = D[uIndex] + weight;
                        pred[i] = uIndex;
                        priorityQueue.changePriority(element, (int) D[i]);
                    }
                }
            }
        }

        int currentVertex = targetIndex;

        while (currentVertex != -1) {
            result.addToFront(vertices[currentVertex]);
            currentVertex = pred[currentVertex];
        }

        return result.iterator();
    }


    public Iterator iteratorDfsBasedAlgorithm(T startVertex, T endVertex) throws EmptyCollectionException, ElementNotFoundException {
        return iteratorDfsBasedAlgorithm(getIndex(startVertex), getIndex(endVertex));
    }

    private Iterator iteratorDfsBasedAlgorithm(int startVertex, int endVertex) throws EmptyCollectionException, ElementNotFoundException {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        if(!indexIsValid(startVertex))
            return resultList.iterator();

        for(int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalStack.push(startVertex);
        resultList.addToRear(vertices[startVertex]);
        visited[startVertex] = true;
        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty())
                traversalStack.pop();
        }

        // Ensure the last vertex is added to the resultList
        if (!traversalStack.isEmpty()) {
            resultList.addToRear(vertices[traversalStack.pop()]);
        }        return resultList.iterator();
    }


    @Override
    public boolean isEmpty() {
        return (numVertices == 0);
    }

    @Override
    public boolean isConnected() {
        try {
            Iterator iterator = iteratorBFS(vertices[0]);
            UnorderedLinkedList<T> list = new UnorderedLinkedList<>();
            while (iterator.hasNext()) {
                T element = (T) iterator.next();
                if (!list.isEmpty())
                    if (list.contains(element))
                        return false;
                list.addToFront(element);
            }
        } catch (EmptyCollectionException e) {
            throw new RuntimeException(e);
        } catch (ElementNotFoundException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
    /**
     * Retrieves an array containing the vertices of the graph.
     *
     * @return An array containing the vertices.
     */
    public T[] getVertices() {
        return vertices;
    }

    /**
     * Retrieves the vertex at the specified index in the graph.
     *
     * @param index The index of the vertex to retrieve.
     * @return The vertex at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public T getVertex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= numVertices) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return vertices[index];
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return The integer number of vertices in the graph.
     */
    public boolean[][] getAdjMatrix() {
        return adjMatrix;
    }

    @Override
    public int size() {
        return vertices.length;
    }
}
