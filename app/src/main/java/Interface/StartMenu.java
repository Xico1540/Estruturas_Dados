package Interface;

import entitys.Player;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidVerticeNumberException;
import implementation_classes.ArrayUnorderedList;
import importer_exporter.Importer;
import implementation_classes.Map;
import java.util.Scanner;

public class StartMenu {

    private final String START_MENU = "\n"+"=".repeat(15)+" Start Menu "+"=".repeat(15)+"\n\n"+
            "\t1. Create Map\n"+
            "\t2. Import Map\n"+
            "\t0. Back\n";


    private final String INVALID_OPTION = "\tInvalid option";

    /**
     * Initiates the game based on user input, allowing the option to create a new map, import a map from a CSV file,
     * create players, and start the game.
     *
     * @return The user's chosen option (0 to exit, 1 to create a new map, 2 to import a map).
     * @throws InvalidVerticeNumberException If an invalid number of vertices is provided during map creation.
     * @throws EmptyCollectionException      If attempting to access an element from an empty collection.
     * @throws ElementNotFoundException      If an expected element is not found in the collection.
     */
    public int start() throws InvalidVerticeNumberException, EmptyCollectionException, ElementNotFoundException {
        Scanner scanner = new Scanner(System.in);
        PlayersMenu playersMenu = new PlayersMenu();
        ArrayUnorderedList<Player> players = new ArrayUnorderedList<>();
        GameMenu gameMenu = new GameMenu();

        int startOption = -1;
        while (startOption!=0) {
            do {
                System.out.println(START_MENU);
                startOption = scanner.nextInt();
                if (startOption < 0 || startOption > 2) System.out.println(INVALID_OPTION);

            } while (startOption < 0 || startOption > 2);

            switch (startOption) {
                case 1:
                    Map createMap = new Map();
                    CreateMapMenu createMapMenu = new CreateMapMenu();
                    createMap=createMapMenu.createMap();
                    players=playersMenu.createPlayers();
                    gameMenu.start(createMap,players);
                    break;
                case 2:
                    Map importMap = new Map();
                    Importer importer = new Importer();
                    importMap = importer.importFromCsv();
                    players=playersMenu.createPlayers();
                    gameMenu.start(importMap,players);
                    break;
                case 0:
                    break;
            }
            if (startOption!=0){

            }
        }



return startOption;
    }

}
