package importer_exporter;

import implementation_classes.Map;

import java.io.FileWriter;
import java.io.IOException;

/**
 * A class responsible for exporting a Map to a CSV file.
 */
public class Exporter {

    /**
     * The output path for the CSV file.
     */
    private final String OUTPUT_PATH = System.getProperty("user.dir") + "\\app\\src\\map.csv";

    /**
     * The content to be exported to the CSV file.
     */
    private String content;

    /**
     * Constructs an Exporter object for a given Map.
     *
     * @param map The Map to be exported.
     */
    public Exporter(Map map) {
        this.content = map.exportToCsvString();
    }

    /**
     * Exports the content to a CSV file.
     */
    public void exportToCsvFile() {
        try (FileWriter writer = new FileWriter(OUTPUT_PATH)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exported map to CSV file successfully!\n");
    }
}
