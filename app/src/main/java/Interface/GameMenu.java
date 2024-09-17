package Interface;

import entitys.Bot;
import entitys.Player;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import gameManagement.GameMap;
import implementation_classes.ArrayUnorderedList;
import implementation_classes.Map;

import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class GameMenu {
    private final String GAME_MENU = "\n" + "=".repeat(15) + " Choose Flag Location " + "=".repeat(15) + "\n\n";

    private final String PLAYER1 = "\t1. Choose Position for Flag Player 1\n";

    private final String PLAYER2 = "\t2. Choose Position for Flag Player 2\n";

    private final String INVALID_OPTION = "\tInvalid option";
    private final String INVALID_POSITION = "\tInvalid position";

    private final String INVALID_POSITIONS = "\tFlags must be further apart than 1 position";

    private final String SAME_POSITION = "\tFlags must be in different positions";

    private final int DEFAULT_PRINT_SIZE = 15;

private final String EXPORT_MENU= "\tDo you want to Export this map?\n"+
        "\t\t1- yes\n"+
        "\t\t0- no";

    private final String ROUND_PLAYER1 = "\n" + "=".repeat(DEFAULT_PRINT_SIZE) + " 🤖 It's PLayer 1 Turn! 🤖" +
            "-".repeat(DEFAULT_PRINT_SIZE) + "\n" ;
    private final String ROUND_PLAYER2 = "\n" + "=".repeat(DEFAULT_PRINT_SIZE) + " 🤖 It's PLayer 2 Turn! 🤖" +
            "-".repeat(DEFAULT_PRINT_SIZE) + "\n" ;
    private int round=1;


    /**
     * Starts the game loop, allowing players and bots to take turns in rounds.
     *
     * @param map     The map representing the game environment.
     * @param players The list of players participating in the game.
     * @throws EmptyCollectionException    If attempting to access an element from an empty collection.
     * @throws ElementNotFoundException    If an expected element is not found in the collection.
     */
    public void start(Map map, ArrayUnorderedList<Player> players) throws EmptyCollectionException, ElementNotFoundException {
        int flagPlayer1;
        int flagPlayer2;
        Scanner scanner = new Scanner(System.in);
        System.out.println(GAME_MENU);
        System.out.println(map.toString());
        int export;
        do {
            System.out.println(EXPORT_MENU);
            export = scanner.nextInt();
            if (export<0 || export>1){
                System.out.println(INVALID_OPTION);
            }
        }while(export<0 || export>1);

        System.out.println();

do {
    System.out.println(PLAYER1);
    flagPlayer1 = scanner.nextInt();

    System.out.println(PLAYER2);
    flagPlayer2 = scanner.nextInt();
    if (flagPlayer1 == flagPlayer2) {
        System.out.println(SAME_POSITION);
    }
    else if (flagPlayer1 < 0 || flagPlayer1 >= map.size() || flagPlayer2 < 0 || flagPlayer2 >= map.size()) {
        System.out.println(INVALID_POSITION);
    }
    else if (flagsConected(flagPlayer1,flagPlayer2,map)){
        System.out.println(INVALID_POSITIONS);
    }
}while(flagPlayer1 == flagPlayer2 ||
        flagPlayer1 < 0 || flagPlayer1 >= map.size() ||
        flagPlayer2 < 0 || flagPlayer2 >= map.size() ||
        flagsConected(flagPlayer1,flagPlayer2,map) );

        GameMap gameMap = new GameMap(map, players, flagPlayer1, flagPlayer2);

        RoundMenu roundMenu = new RoundMenu();



        Random random = new Random();
        int firstPlayer = random.nextInt(0,1);

        if (firstPlayer ==0){
            while (true) {
                Iterator<Player> playersList = players.iterator();
                Player player1 = playersList.next();
                Player player2 = playersList.next();
                Iterator<Bot> bot1 = player1.getBots().iterator();
                Iterator<Bot> bot2 = player2.getBots().iterator();
                System.out.println("#================================================#");
                System.out.println("||                     Round "+round+"                  ||");
                System.out.println("#================================================#");
                for (int i = 0; i < player1.getBots().size(); i++) {
                    System.out.println(ROUND_PLAYER1);
                    roundMenu.round(bot1.next(), gameMap);
                    System.out.println(ROUND_PLAYER2);
                    roundMenu.round(bot2.next(), gameMap);
                }
                round++;
                System.out.println();
                System.out.println(map.toString());
            }
        }else {
            while (true) {
                Iterator<Player> playersList = players.iterator();
                Player player1 = playersList.next();
                Player player2 = playersList.next();
                System.out.println("#================================================#");
                System.out.println("||                     Round "+round+"                  ||");
                System.out.println("#================================================#");
                for (int i = 0; i < player1.getBots().size(); i++) {
                    Iterator<Bot> bot1 = player1.getBots().iterator();
                    Iterator<Bot> bot2 = player2.getBots().iterator();
                    System.out.println(ROUND_PLAYER2);
                    roundMenu.round(bot2.next(), gameMap);
                    System.out.println(ROUND_PLAYER1);
                    roundMenu.round(bot1.next(), gameMap);
                }
                round++;
                System.out.println();
                System.out.println(map.toString());
            }
        }

    }

    private boolean flagsConected(int flagPlayer1, int flagPlayer2, Map map){
        boolean[][] matrix = map.getAdjMatrix();
        if (matrix[flagPlayer1][flagPlayer2] || matrix[flagPlayer2][flagPlayer1]){
            return true;
        }
        return false;
    }
}
