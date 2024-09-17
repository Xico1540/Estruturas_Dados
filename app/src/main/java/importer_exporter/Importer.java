package importer_exporter;

import entitys.Position;
import implementation_classes.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class responsible for importing a Map from a CSV file.
 */
public class Importer {

    /**
     * The input path for the CSV file.
     */
    private static final String INPUT_PATH = System.getProperty("user.dir") + "\\app\\src\\map.csv";

    /**
     * Imports a Map from a CSV file.
     *
     * @return The imported Map.
     */
    public Map importFromCsv() {
        Map map = new Map();

        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_PATH))) {

            // Read the number of vertices
            String numVerticesLine = reader.readLine();
            int numVertices;
            try {
                 numVertices = Integer.parseInt(numVerticesLine.split(":")[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number of vertices in CSV file!");
                return null;
            }
            // Add vertices to the map
            for (int i = 0; i < numVertices; i++) {
                Position position = new Position(null); // You might need to provide a valid argument for the Position constructor
                map.addVertex(position);
            }

            // Ignore line
            reader.readLine();

            String line;
            int source=-1;
            int target=-1;
            double weight=-1;

            // Read edges and weights
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                try {


                    source = Integer.parseInt(parts[0]);
                    target = Integer.parseInt(parts[1]);
                    weight = Double.parseDouble(parts[2]);
                }catch (NumberFormatException e) {
                    System.out.println("Invalid edge in CSV file!");
                }

                map.addEdge(map.getVertex(source), map.getVertex(target), weight);

            }

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Imported map from CSV file successfully!\n");
        return map;
    }
}
