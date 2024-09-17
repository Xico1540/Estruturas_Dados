package implementation_classes;

import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import implementation_classes.Lists.DoublyUnorderedLinkedList;
import implementation_classes.Queues.LinkedQueue;

import java.text.DecimalFormat;

import java.util.Iterator;


/**
 * Represents a network (graph) with weighted edges.
 *
 * @param <T> The type of elements stored in the network.
 */
public class Network<T> extends Graph<T> implements NetworkADT<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int EXPAND_BY = 2;
    protected double[][] weightMatrix;

    /**
     * Constructs an empty network.
     */
    public Network() {
        super.numVertices = 0;
        weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    /**
     * Adds a vertex to the network.
     *
     * @param vertex The vertex to be added.
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandWeightCapacity();
        }

        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            weightMatrix[numVertices][i] = 0;
            weightMatrix[i][numVertices] = 0;
        }

        numVertices++;
    }

    /**
     * Expands the weight matrix capacity when the number of vertices exceeds the current capacity.
     */
    protected void expandWeightCapacity() {
        T[] temporaryArray = vertices;
        vertices = (T[]) (new Object[vertices.length * EXPAND_BY]);

        for (int i = 0; i < numVertices; i++)
            vertices[i] = temporaryArray[i];

        double[][] temporaryWeightMatrix = weightMatrix;
        weightMatrix = new double[weightMatrix.length * EXPAND_BY][weightMatrix.length * EXPAND_BY];

        for (int i = 0; i < temporaryWeightMatrix.length; i++)
            for (int j = 0; j < temporaryWeightMatrix.length; j++)
                weightMatrix[i][j] = temporaryWeightMatrix[i][j];

        boolean[][] temporaryAdjMatrix = adjMatrix;
        adjMatrix = new boolean[adjMatrix.length * EXPAND_BY][adjMatrix.length * EXPAND_BY];

        for (int i = 0; i < temporaryAdjMatrix.length; i++)
            for (int j = 0; j < temporaryAdjMatrix.length; j++)
                adjMatrix[i][j] = temporaryAdjMatrix[i][j];
    }

    /**
     * Adds a weighted edge between two vertices in the network.
     *
     * @param firstVertex  The first vertex.
     * @param secondVertex The second vertex.
     * @param weight       The weight of the edge.
     */
    @Override
    public void addEdge(T firstVertex, T secondVertex, double weight) {
        try {
            addEdge(getIndex(firstVertex), getIndex(secondVertex), weight);
        } catch (ElementNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addEdge(int firstVertex, int secondVertex, double weight) {
        if (indexIsValid(firstVertex) && indexIsValid(secondVertex)) {
            weightMatrix[firstVertex][secondVertex] = weight;
            adjMatrix[firstVertex][secondVertex] = true;
        }
    }

    /**
     * Calculates the shortest path weight between two vertices using Dijkstra's algorithm.
     *
     * @param firstVertex  The source vertex.
     * @param secondVertex The target vertex.
     * @return The shortest path weight between the source and target vertices.
     * @throws ElementNotFoundException If either the source or target vertex is not found in the network.
     */
    @Override
    public double shortestPathWeight(T startVertex, T endVertex) throws ElementNotFoundException {
        Double[] distance = new Double[numVertices];
        boolean[] visited = new boolean[numVertices];

        // Preencher o array de distâncias com infinito
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Double.POSITIVE_INFINITY;
        }

        distance[getIndex(startVertex)] = 0.0;

        for (int count = 0; count < numVertices - 1; count++) {
            int currentVertex = getMinimumDistanceVertex(distance, visited);
            visited[currentVertex] = true;

            for (int neighborVertex = 0; neighborVertex < numVertices; neighborVertex++) {
                if (!visited[neighborVertex] && weightMatrix[currentVertex][neighborVertex] != 0 && distance[currentVertex] != Double.POSITIVE_INFINITY
                        && distance[currentVertex] + weightMatrix[currentVertex][neighborVertex] < distance[neighborVertex]) {
                    distance[neighborVertex] = distance[currentVertex] + weightMatrix[currentVertex][neighborVertex];
                }
            }
        }

        return distance[getIndex(endVertex)] == Double.POSITIVE_INFINITY ? -1 : distance[getIndex(endVertex)];
    }

    private int getMinimumDistanceVertex(Double[] distance, boolean[] visited) {
        double minDistance = Double.POSITIVE_INFINITY;
        int minDistanceVertexIndex = -1;

        for (int vertex = 0; vertex < numVertices; vertex++) {
            if (!visited[vertex] && distance[vertex] <= minDistance) {
                minDistance = distance[vertex];
                minDistanceVertexIndex = vertex;
            }
        }

        return minDistanceVertexIndex;
    }


    /**
     * Returns a string representation of the network.
     *
     * @return A string representation of the network.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        DecimalFormat format = new DecimalFormat("#.#");
        for (int i = 0; i < numVertices; i++) {
            result.append(" ✨ Position #").append(i).append(" (").append(vertices[i].toString()).append(") ➤ ");

            boolean hasNeighbor = false;
            for (int j = 0; j < numVertices; j++) {
                if (weightMatrix[i][j] != 0) {
                    if (hasNeighbor) {
                        result.append(" ");
                    }
                    double num = weightMatrix[i][j];
                    String numFormatted = format.format(num);
                    result.append("  Position #").append(j).append(" (").append(numFormatted).append(") ");
                    hasNeighbor = true;
                }
            }

            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Exports the network to a CSV string format.
     *
     * @return A CSV string representation of the network.
     */
    public String exportToCsvString() {
        StringBuilder csvString = new StringBuilder();

        csvString.append("numVertices:").append(numVertices).append("\n");

        csvString.append("source,target,weight\n");

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (weightMatrix[i][j] != 0) {
                    csvString.append(i).append(",").append(j).append(",").append(weightMatrix[i][j]).append("\n");
                }
            }
        }

        return csvString.toString();
    }

    /**
     * Returns an iterator over the vertices in the shortest path between two vertices using Breadth-First Search (BFS).
     *
     * @param startVertex  The starting vertex.
     * @param targetVertex The target vertex.
     * @return An iterator over the vertices in the shortest path.
     * @throws EmptyCollectionException If the network is empty.
     * @throws ElementNotFoundException If either the start or target vertex is not found in the network.
     */
    public Iterator<T> iteratorShortestPathBFS(T startVertex, T targetVertex) throws EmptyCollectionException, ElementNotFoundException {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            throw new IllegalArgumentException("Invalid start or target vertex");
        }

        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        DoublyUnorderedLinkedList<T> resultList = new DoublyUnorderedLinkedList<>();
        int[] numVerticesRun = new int[numVertices];
        int[] previousVertices = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            numVerticesRun[i] = Integer.MAX_VALUE;
            previousVertices[i] = -1;
            visited[i] = false;
        }

        numVerticesRun[startIndex] = 0;
        traversalQueue.enqueue(startIndex);

        while (!traversalQueue.isEmpty()) {
            int currentVertex = traversalQueue.dequeue();

            if (currentVertex == targetIndex) {
                int backtrackVertex = targetIndex;
                while (backtrackVertex != -1) {
                    resultList.addToFront(vertices[backtrackVertex]);
                    backtrackVertex = previousVertices[backtrackVertex];
                }
                return resultList.iterator();
            }

            for (int adjacentVertex = 0; adjacentVertex < numVertices; adjacentVertex++) {
                if (weightMatrix[currentVertex][adjacentVertex] > 0 && !visited[adjacentVertex]) {
                    traversalQueue.enqueue(adjacentVertex);
                    visited[adjacentVertex] = true;

                    // Update the number of vertices run
                    int verticesRun = numVerticesRun[currentVertex] + 1;
                    if (verticesRun < numVerticesRun[adjacentVertex]) {
                        numVerticesRun[adjacentVertex] = verticesRun;
                        previousVertices[adjacentVertex] = currentVertex;
                    }
                }
            }
        }
        return resultList.iterator();
    }



    public Iterator<T> iteratorBelmanFord(T startVertex, T targetVertex) throws ElementNotFoundException, EmptyCollectionException {
        return iteratorBelmanFord(getIndex(startVertex), getIndex(targetVertex));
    }
    private Iterator<T> iteratorBelmanFord(int startIndex, int targetIndex) throws ElementNotFoundException, EmptyCollectionException {
        ArrayUnorderedList<T> result = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            throw new IllegalArgumentException("Invalid start or target vertex");
        }

        double[] distance = new double[numVertices];
        int[] parent = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distance[i] = Double.POSITIVE_INFINITY;
            parent[i] = -1;
        }

        distance[startIndex] = 0;

        for (int i = 1; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                for (int k = 0; k < numVertices; k++) {
                    if (adjMatrix[j][k] && distance[j] != Double.POSITIVE_INFINITY && distance[j] + weightMatrix[j][k] < distance[k]) {
                        distance[k] = distance[j] + weightMatrix[j][k];
                        parent[k] = j;
                    }
                }
            }

            // If the target vertex has been visited, stop the algorithm
            if (distance[targetIndex] != Double.POSITIVE_INFINITY) {
                break;
            }
        }

        // Build the path from the start vertex to the target vertex
        LinkedStack<Integer> stack = new LinkedStack<>();
        int vertex = targetIndex;
        while (parent[vertex] != -1) {
            stack.push(vertex);
            vertex = parent[vertex];
        }
        stack.push(startIndex);

        // Add the vertices in the path to the result list
        while (!stack.isEmpty()) {
            result.addToRear(vertices[stack.pop()]);
        }

        return result.iterator();
    }


    /**
     * Returns an iterator over the vertices in the shortest path between two vertices based on edge weights using Dijkstra's algorithm.
     *
     * @param startVertex  The starting vertex.
     * @param targetVertex The target vertex.
     * @return An iterator over the vertices in the shortest path.
     * @throws ElementNotFoundException If either the start or target vertex is not found in the network.
     * @throws EmptyCollectionException If the network is empty.
     */
    public Iterator<T> iteratorShortestPathWeight(T startVertex, T targetVertex) throws ElementNotFoundException, EmptyCollectionException {
        return iteratorShortestPathWeight(getIndex(startVertex), getIndex(targetVertex));
    }

    private Iterator<T> iteratorShortestPathWeight(int startIndex, int targetIndex) throws ElementNotFoundException, EmptyCollectionException {
        ArrayUnorderedList<T> result = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            throw new IllegalArgumentException("Invalid start or target vertex");
        }

        int[] parent = new int[numVertices];
        double[] distance = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        distance[startIndex] = 0;

        for (int i = 0; i < numVertices; i++) {
            if (i != startIndex) {
                distance[i] = Double.POSITIVE_INFINITY;
            }
            parent[i] = -1;
        }

        for (int count = 0; count < numVertices - 1; count++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && adjMatrix[u][v] && distance[u] != Double.POSITIVE_INFINITY && distance[u] + 1.0 < distance[v]) {
                    distance[v] = distance[u] + 1.0;
                    parent[v] = u;
                }
            }
        }

        int currentVertex = targetIndex;

        while (currentVertex != -1) {
            result.addToFront(vertices[currentVertex]);
            currentVertex = parent[currentVertex];
        }

        return result.iterator();
    }

    private int minDistance(double[] distance, boolean[] visited) {
        double min = Double.POSITIVE_INFINITY;
        int minIndex = -1;

        for (int v = 0; v < numVertices; v++) {
            if (!visited[v] && distance[v] <= min) {
                min = distance[v];
                minIndex = v;
            }
        }

        return minIndex;
    }
}
