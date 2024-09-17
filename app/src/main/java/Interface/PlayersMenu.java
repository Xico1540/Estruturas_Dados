package Interface;

import algorithms.Algorithm;
import algorithms.BelmanFord;
import algorithms.ShortestPathBFS;
import algorithms.ShortestPathWeight;
import entitys.Bot;
import entitys.Flag;
import entitys.Player;
import implementation_classes.ArrayUnorderedList;


import java.util.Scanner;

public class PlayersMenu {
    private final String PLAYERS_MENU = "\n" + "=".repeat(15) + " Players Menu " + "=".repeat(15) + "\n\n";

    private final String NAME_PLAYER1 = "\n\t1. Enter player 1 name: \n";

    private final String INVALID_OPTION = "\tInvalid option\n";

    private final String NAME_PLAYER2 = "\n\t2. Enter player 2 name: \n";

    private final String NUM_BOTS = "\n\t3. How many bots: \n";

    private final String BOT_PLAYER1 = "\n\t\tCreate bot for Player 1: \n\n";

    private final String BOT_PLAYER2 = "\n\t\t Create bot for Player 2: \n\n";

    private final String BOT_LIMIT = "\t\t\t Number of bots must be between 1 and 4   \n";

    private final String FLAG_NAME1 = "\n\t\t\t Enter Player 1 flag name: \n";
    private final String FLAG_NAME2 = "\n\t\t\t Enter PLayer 2 flag name: \n";

    private final String BOT_ALGORITHM = " Choose bot algorithm:\n\n" +
            "\t1-Bellman BelFord\n" +
            "\t2-Dijkstra's Shortest Path\n" +
            "\t3-Breadth-First Search Algorithm\n";

    /**
     * Creates a list of players for the game, allowing user input for player names, bots, and flags.
     *
     * @return An ArrayUnorderedList of Player objects representing the players in the game.
     */
        public ArrayUnorderedList<Player> createPlayers() {
            Scanner scanner = new Scanner(System.in);
            ArrayUnorderedList<Player> players = new ArrayUnorderedList<>();
            System.out.println(PLAYERS_MENU);

            // player names
            System.out.println(NAME_PLAYER1);
            String namePlayer1 = scanner.nextLine();
            String namePlayer2 = null;
            do {
                System.out.println(NAME_PLAYER2);
                namePlayer2 = scanner.nextLine();
                if (namePlayer1.equals(namePlayer2)) {
                    System.out.println(INVALID_OPTION);
                }
            } while (namePlayer1.equals(namePlayer2));

            // create bots
            int numBots = -1;
            System.out.println(NUM_BOTS);
            while (numBots < 1 || numBots > 4) {
                numBots = scanner.nextInt();
                if (numBots < 1 || numBots > 4) System.out.println(BOT_LIMIT);
            }
            System.out.println(BOT_PLAYER1);
            ArrayUnorderedList<Bot> botsPlayer1 = createBotsPlayer(numBots," Player 1");
            System.out.println(BOT_PLAYER2);
            ArrayUnorderedList<Bot> botsPlayer2 = createBotsPlayer(numBots," Player 2");

            // create flags
            String flagName2;

            System.out.println(FLAG_NAME1);
            scanner.nextLine();//flush
            String flagName1 = scanner.nextLine();

            Flag flagPlayer1 = new Flag(flagName1);
            do {
                System.out.println(FLAG_NAME2);
                flagName2 = scanner.nextLine();
                if (flagName1.equals(flagName2)) {
                    System.out.println(INVALID_OPTION);
                }
            } while (flagName1.equals(flagName2));
            Flag flagPlayer2 = new Flag(flagName2);

            // create players
            Player player1 = new Player(namePlayer1, botsPlayer1, flagPlayer1);
            Player player2 = new Player(namePlayer2, botsPlayer2, flagPlayer2);
            players.addToRear(player1);
            players.addToRear(player2);
            return players;
        }

    /**
     * Creates a list of bots for a player, including their names and algorithms.
     *
     * @param numBots    The number of bots to create for the player.
     * @param playerName The name of the player associated with the bots.
     * @return An ArrayUnorderedList of Bot objects representing the bots for a player.
     */
        private ArrayUnorderedList<Bot> createBotsPlayer(int numBots, String playerName) {
            ArrayUnorderedList<Bot> botsPlayer = new ArrayUnorderedList<>();
            Scanner scanner = new Scanner(System.in);
            int[] botAlgorithms = new int[3];
            int botAlgorithm;
            for (int i = 0; i < numBots; i++) {
                System.out.println("Bot number " + (i + 1) + ":\n");
                do {
                    System.out.println(BOT_ALGORITHM);
                    botAlgorithm = scanner.nextInt();
                    if (botAlgorithm < 1 || botAlgorithm > 3 || !notAvailableAlgorithm(botAlgorithm, botAlgorithms)) {
                        System.out.println(INVALID_OPTION);
                    }
                } while (!notAvailableAlgorithm(botAlgorithm, botAlgorithms) || botAlgorithm < 1 || botAlgorithm > 3);
                Algorithm algorithm;
                String name;
                switch (botAlgorithm) {
                    case 1:
                        algorithm = new BelmanFord();
                        name = "BelmanFord "+i+playerName;
                        Bot bot1 = new Bot(name, algorithm);
                        botsPlayer.addToRear(bot1);
                        botAlgorithms[0] = botAlgorithm;
                        break;
                    case 2:
                        algorithm = new ShortestPathWeight();
                        name = "ShortestPathWeight "+i+playerName;
                        Bot bot2 = new Bot(name, algorithm);
                        botsPlayer.addToRear(bot2);
                        botAlgorithms[1] = botAlgorithm;
                        break;
                    case 3:
                        algorithm = new ShortestPathBFS();
                        name = "ShortestPathBFS "+i+playerName;
                        Bot bot3 = new Bot(name, algorithm);
                        botsPlayer.addToRear(bot3);
                        botAlgorithms[2] = botAlgorithm;
                        break;
                }


            }
            return botsPlayer;
        }

    /**
     * Checks if a bot algorithm is not already selected for a player.
     *
     * @param botAlgorithm   The algorithm to check.
     * @param botAlgorithms  An array representing selected bot algorithms for a player.
     * @return True if the algorithm is not already selected, false otherwise.
     */
        private boolean notAvailableAlgorithm(int botAlgorithm, int[] botAlgorithms) {
            int count = 0;

            for (int i = 0; i < botAlgorithms.length; i++) {
                if (botAlgorithms[i] != 0) {
                    count++;
                }
            }

            if (count == 3) {
                return true;
            }

            if (botAlgorithms[botAlgorithm - 1] == 0) {
                return true;
            }
            return false;

        }
    }




