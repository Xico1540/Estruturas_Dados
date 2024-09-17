package Interface;

import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidVerticeNumberException;

import java.util.Scanner;

public class MainMenu {
private static final int DEFAULT_SPACE = 15;
    private static final String START = "\n$$$$$$$$  $$$$$$$$$$  $$$$$$$$$   $$$$$$$   $$$$$$$$$$\n" +
        "$$            $$      $$     $$   $$    $$      $$    \n" +
        "$$$$$$$$      $$      $$$$$$$$$   $$$$$$        $$    \n" +
        "      $$      $$      $$     $$   $$   $$       $$    \n" +
        "$$$$$$$$      $$      $$     $$   $$    $$      $$    \n";

    private static final String MAIN_MENU = "\n"+"=".repeat(DEFAULT_SPACE)+" Main Menu "+"=".repeat(DEFAULT_SPACE)+"\n\n"+
            "\t1. Start Game\n"+
            "\t2. Instructions\n"+
        "\t0. Exit\n";

    private static final String INSTRUCTIONS =
            "\n" + "=".repeat(DEFAULT_SPACE) + " INSTRUCTIONS " + "=".repeat(DEFAULT_SPACE) + "\n\n" +
                    "\tWelcome to the Capture The Flag game!\n\n" +
                    "\tObjective:\n" +
                    "\t  Your goal is to capture the enemy's flag.\n\n" +
                    "\tGame Setup:\n" +
                    "\t  1. Players must choose the number of bots for their teams.\n" +
                    "\t  2. Each bot has its own algorithm to decide actions.\n" +
                    "\t  3. Enter map parameters:\n" +
                    "\t      - Number of vertices (10-100)\n" +
                    "\t      - Density (0.15-1.0)\n" +
                    "\t      - Directed or Undirected map\n" +
                    "\t  4. The game board is generated based on the chosen map parameters.\n" +
                    "\t  5. Players choose their flag positions.\n" +
                    "\t  6. Optionally, you can import/export maps before playing.\n\n" +
                    "\tGameplay:\n" +
                    "\t  1. Players take turns making moves.\n" +
                    "\t  2. Bots move towards the enemy base or attempt to capture the flag to win.\n" +
                    "\n\tHave fun and may the luckiest Player win!\n";



    /**
     * Starts the game loop, allowing players and bots to take turns in rounds.
     *
     * @param map     The map representing the game environment.
     * @param players The list of players participating in the game.
     * @throws EmptyCollectionException    If attempting to access an element from an empty collection.
     * @throws ElementNotFoundException    If an expected element is not found in the collection.
     */
    private static final String INVALID_OPTION = "\tInvalid option";

    public static void main(String[] args) throws InvalidVerticeNumberException, EmptyCollectionException, ElementNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(START);
        int option;

        while (true) {
            do {
                System.out.println(MAIN_MENU);
                option = scanner.nextInt();
                if (option < 0 || option > 2) System.out.println(INVALID_OPTION);
            } while (option < 0 || option > 2);

            switch (option) {
                case 1:
                    StartMenu startMenu = new StartMenu();
                    startMenu.start();
                    break;
                case 2:
                    System.out.println(INSTRUCTIONS);
                    break;
                case 0:
                    System.exit(0);
                    break;

                default:

                    break;
            }

        }
    }

}
