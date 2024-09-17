package implementation_classes;

import entitys.Position;
import exceptions.InvalidVerticeNumberException;

import java.util.Random;

/**
 * Map represents a network of positions (vertices) in a directed or non-directed graph.
 * The graph can be generated with specified characteristics like the number of vertices and density.
 */
public class Map extends Network<Position> {

    /**
     * Constructs an empty Map.
     */
    public Map() {
        super();
    }

    /**
     * Generates a directed map with the specified number of vertices and density.
     *
     * @param numberOfVertices The number of vertices in the map.
     * @param density          The density of the map.
     * @throws InvalidVerticeNumberException If the number of vertices or density is invalid.
     */
    public void directedMap(int numberOfVertices, double density) throws InvalidVerticeNumberException {
        // Validation for the number of vertices
        if (numberOfVertices < 10 || numberOfVertices > 100) {
            throw new InvalidVerticeNumberException("The number of vertices must be between 10 and 100");
        }
        // Validation for the density
        if (density < 0.10 || density > 0.5) {
            throw new InvalidVerticeNumberException("The density must be between 1 and 0.25");
        }

        // Calculating the expected number of edges
        Integer expectedNumberOfEdges = Math.toIntExact(Math.round((numberOfVertices * (numberOfVertices - 1)) * density));

        // Creating a list of vertices (positions)
        Position[] positions = new Position[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            Position position = new Position(null);
            addVertex(position);
            positions[i] = position;
        }

        // Initializing an array to check if all vertices have been visited
        boolean[] visited = new boolean[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            visited[i] = false;
        }

        // Creating a random object
        Random random = new Random();

        // Generating a connected and possibly cyclic directed graph
        while (!allVisited(visited)) {
            int firstVertexIndex = random.nextInt(numberOfVertices);
            int secondVertexIndex = random.nextInt(numberOfVertices);

            // Checks if the vertices are different and if they have not been visited yet
            if (firstVertexIndex != secondVertexIndex && !visited[firstVertexIndex] && !visited[secondVertexIndex]) {
                // Gets the vertices
                Position firstVertex = getVertex(firstVertexIndex);
                Position secondVertex = getVertex(secondVertexIndex);

                // Assigning a random cost to the edge
                Double cost = random.nextDouble(1, 15);
                addEdge(firstVertex, secondVertex, cost);

                // Sets both vertices to visited
                visited[firstVertexIndex] = true;
                visited[secondVertexIndex] = true;
            }
        }

        // Connecting the graph with additional edges if needed
        while (getNumberOfEdges() < expectedNumberOfEdges) {
            int firstVertexIndex = random.nextInt(numberOfVertices);
            int secondVertexIndex = random.nextInt(numberOfVertices);
            if (firstVertexIndex != secondVertexIndex) {
                Position firstVertex = getVertex(firstVertexIndex);
                Position secondVertex = getVertex(secondVertexIndex);

                // Assigning a random cost to the edge
                Double cost = random.nextDouble(1, 15);
                addEdge(firstVertex, secondVertex, cost);
            }
        }
    }

    /**
     * Generates a non-directed map with the specified number of vertices and density.
     *
     * @param numberOfVertices The number of vertices in the map.
     * @param density          The density of the map.
     * @throws InvalidVerticeNumberException If the number of vertices or density is invalid.
     */
    public void nonDirectedMap(int numberOfVertices, double density) throws InvalidVerticeNumberException {
        // Validation for the number of vertices
        if (numberOfVertices < 10 || numberOfVertices > 100) {
            throw new InvalidVerticeNumberException("The number of vertices must be between 10 and 100");
        }
        // Validation for the density
        if (density < 0.10 || density > 0.5) {
            throw new InvalidVerticeNumberException("The density must be between 1 and 0.25");
        }

        // Calculating the expected number of edges
        Integer expectedNumberOfEdges = Math.toIntExact(Math.round((numberOfVertices * (numberOfVertices - 1)) * density));

        // Creating a list of vertices (positions)
        Position[] positions = new Position[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            Position position = new Position(null);
            addVertex(position);
            positions[i] = position;
        }

        // Initializing an array to check if all vertices have been visited
        boolean[] visited = new boolean[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            visited[i] = false;
        }

        // Creating a random object
        Random random = new Random();

        // Generating a connected and possibly cyclic non-directed graph
        while (!allVisited(visited)) {
            int firstVertexIndex = random.nextInt(numberOfVertices);
            int secondVertexIndex = random.nextInt(numberOfVertices);

            // Checks if the vertices are different and if they have not been visited yet
            if (firstVertexIndex != secondVertexIndex && !visited[firstVertexIndex] && !visited[secondVertexIndex]) {
                // Gets the vertices
                Position firstVertex = getVertex(firstVertexIndex);
                Position secondVertex = getVertex(secondVertexIndex);

                // Assigning a random cost to the edge
                Double cost = random.nextDouble(1, 15);
                addEdge(firstVertex, secondVertex, cost);
                addEdge(secondVertex, firstVertex, cost);

                // Sets both vertices to visited
                visited[firstVertexIndex] = true;
                visited[secondVertexIndex] = true;
            }
        }

        // Connecting the graph with additional edges if needed
        while (getNumberOfEdges() < expectedNumberOfEdges) {
            int firstVertexIndex = random.nextInt(numberOfVertices);
            int secondVertexIndex = random.nextInt(numberOfVertices);
            if (firstVertexIndex != secondVertexIndex) {
                Position firstVertex = getVertex(firstVertexIndex);
                Position secondVertex = getVertex(secondVertexIndex);

                // Assigning a random cost to the edge
                Double cost = random.nextDouble(1, 15);
                addEdge(firstVertex, secondVertex, cost);
                addEdge(secondVertex, firstVertex, cost);
            }
        }
    }

    /**
     * Gets the total number of edges in the map.
     *
     * @return The total number of edges in the map.
     */
    private int getNumberOfEdges() {
        int count = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (weightMatrix[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if all vertices have been visited.
     *
     * @param visited An array representing the visited status of each vertex.
     * @return True if all vertices have been visited, false otherwise.
     */
    private boolean allVisited(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }
}