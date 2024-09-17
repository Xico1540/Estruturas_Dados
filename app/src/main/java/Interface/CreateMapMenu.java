package Interface;

import exceptions.InvalidVerticeNumberException;
import implementation_classes.Map;

import java.util.Scanner;

public class CreateMapMenu {

    private final int DEFAULT_PRINT_SIZE = 15;
    private final String CREATE_MAP = "\n" + "=".repeat(DEFAULT_PRINT_SIZE) + " Create Map " + "=".repeat(DEFAULT_PRINT_SIZE) + "\n\n";

    private final String NUM_VERTICES = "\t1. How many vertices do you want?\n\n" +
            "Enter a number between 10 and 100: ";

    private final String DENSITY = "\t2. What density do you want?\n\n" +
            "Enter a number between 0,15 and 0,5: ";

    private final String DIRECTED = "\t3. Do you want a directed or undirected map?\n\n" +
            " Enter 1 for directed or 2 for undirected: ";

    private final String INVALID_OPTION = "\tInvalid option";

    private final String MAP_CREATED="Map created sucessfully!!";


    /**
     * Creates a graph-based map by interacting with the user through the console.
     * The method prompts the user for the number of vertices, density, and whether
     * the graph should be directed or not. It validates user input to ensure it
     * falls within specified ranges. The method then creates and returns a Map
     * object representing the generated graph-based map.
     *
     * @return A Map object representing the generated graph-based map.
     * @throws InvalidVerticeNumberException If an invalid number of vertices is provided.
     */
    public Map createMap() throws InvalidVerticeNumberException {
        Scanner scanner = new Scanner(System.in);
        Map map = new Map();
        int numVertices = -1;
        double density = -1;
        int directed = -1;
        System.out.println(CREATE_MAP);

        System.out.println(NUM_VERTICES);
        while (numVertices < 10 || numVertices > 100) {
            numVertices = scanner.nextInt();
            if (numVertices < 10 || numVertices > 100) System.out.println(INVALID_OPTION);
        }

        System.out.println(DENSITY);
        while (density < 0.15 || density > 0.5) {
            density = scanner.nextDouble();
            if (density < 0.15 || density > 0.5) System.out.println(INVALID_OPTION);
        }

        System.out.println(DIRECTED);
        while (directed < 1 || directed > 2) {
            directed = scanner.nextInt();
            if (directed < 1 || directed > 2) System.out.println(INVALID_OPTION);
        }
        if (directed == 1) {
        map.directedMap(numVertices,0.3);
        } else {
        map.nonDirectedMap(numVertices,density);
        }

        if (map!=null) {
            System.out.println(MAP_CREATED);
        }
        return map;
    }
}
