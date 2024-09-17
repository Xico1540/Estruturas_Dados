/**
 * Manages the game map, including player turns and bot movements.
 */
package gameManagement;

import entitys.*;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import implementation_classes.ArrayUnorderedList;
import implementation_classes.Map;

import java.util.Iterator;

public class GameMap {

    private final Map map;    // Map of the game
    private final ArrayUnorderedList<Player> players;   // Array of players

    private final int DEFAULT_PRINT_SIZE = 15;  // Default size of the map to print

    /**
     * Constructs a new GameMap with the specified map, array of players, and initial flag positions.
     *
     * @param map The map of the game.
     * @param players The array of players.
     * @param flagPlayer1 The initial position of the flag for the first player.
     * @param flagPlayer2 The initial position of the flag for the second player.
     */
    public GameMap(Map map, ArrayUnorderedList<Player> players, int flagPlayer1, int flagPlayer2) {
        this.map = map;
        this.players = players;
        initializeMap(flagPlayer1, flagPlayer2);
    }

    /**
     * Initializes the map with player flags at specified positions.
     *
     * @param flagPlayer1 The initial position of the flag for the first player.
     * @param flagPlayer2 The initial position of the flag for the second player.
     * @throws IllegalArgumentException if the flags are in the same position or in an invalid position.
     */
    private void initializeMap(int flagPlayer1, int flagPlayer2) {
        if (flagPlayer1 == flagPlayer2) {
            throw new IllegalArgumentException("The flags must be in different positions");
        }
        if (flagPlayer1 < 0 || flagPlayer1 >= map.size() || flagPlayer2 < 0 || flagPlayer2 >= map.size()) {
            throw new IllegalArgumentException("The flags must be in a valid position");
        }

        Object[] rawPositions = map.getVertices();
        Position[] positions = new Position[rawPositions.length];

        for (int i = 0; i < rawPositions.length; i++) {
            positions[i] = (Position) rawPositions[i];
        }


        for (int i = 0; i < map.size(); i++) {
            if (i == flagPlayer1) {
                try {
                    players.first().getFlag();
                }catch (Exception exception){  throw new IllegalArgumentException("There must be a flag for the first player");}
                positions[i].setEntity(players.first().getFlag());
            }
            if (i == flagPlayer2) {
                try {
                    players.last().getFlag();
                }catch (Exception exception){  throw new IllegalArgumentException("There must be a flag for the second player");}
                positions[i].setEntity(players.last().getFlag());
            }
        }
    }

    /**
     * Executes a round of the game for the specified bot.
     *
     * @param bot The bot for which to execute a round.
     * @throws EmptyCollectionException if the map is empty.
     * @throws ElementNotFoundException if the bot is not found in the game.
     */
    public int round(Bot bot) throws EmptyCollectionException, ElementNotFoundException {
        // Find the player
        Player player = findPlayer(bot);
        if (player == null) {
            throw new ElementNotFoundException("This bot is not in the game");
        }

        // Find the bot flag
        int teamFlagPosition = findEntity(player.getFlag());

        //find bot position
        int botPosition = findEntity(bot);
        if (botPosition == -1) {
            botPosition = teamFlagPosition;
        }


        // Find the enemy position
        int enemyPosition = findEnemyPosition(bot);
        if (enemyPosition == -1) {
            throw new ElementNotFoundException("There is no enemy");
        }

        // Find the next move
        int nextMove = bot.nextMove(map, map.getVertex(botPosition), map.getVertex(enemyPosition));

        System.out.println("Bot " + bot.getName() + " is moving from " + botPosition + " to " + nextMove);

        // Winning condition = 1
        if (nextMove == enemyPosition) {
            return 1;
        }

        // Obstacle encountered = 0
        else if (!isEmpty(nextMove)) {
           return 0;
        }

        // Successful move = -1
        else {
            if (botPosition != teamFlagPosition) {
                map.getVertex(botPosition).setEntity(null);
            }

            map.getVertex(nextMove).setEntity(bot);
            bot.resetSkipRound();
            return -1;
        }
    }

    /**
     * Checks if a specified position on the map is empty.
     *
     * @param position The position to check.
     * @return true if the position is empty, false otherwise.
     */
    private boolean isEmpty(int position) {
        Object[] rawPositions = map.getVertices();
        Position[] positions = new Position[rawPositions.length];

        for (int i = 0; i < rawPositions.length; i++) {
            positions[i] = (Position) rawPositions[i];
        }

        for (int i = 0; i < map.size(); i++) {
            if (i == position && positions[i].getEntity() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the game map.
     *
     * @return The game map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Finds the position of a specified entity on the map.
     *
     * @param entity The entity to find.
     * @return The position of the entity, or -1 if not found.
     * @throws IllegalArgumentException if the entity is null.
     */
    private int findEntity(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("The entity can not be null");
        }
        int entityPosition = -1;

        Object[] rawPositions = map.getVertices();
        Position[] positions = new Position[rawPositions.length];

        for (int i = 0; i < rawPositions.length; i++) {
            positions[i] = (Position) rawPositions[i];
        }

        for (int i = 0; i < map.size(); i++) {
            Position position = positions[i];

            if (position != null) {
                Entity findEntity = position.getEntity();
                // Check if the position is not empty and if the entity has the same name
                if (findEntity != null && findEntity.getName().equals(entity.getName())) {
                    entityPosition = i;
                    return entityPosition;
                }
            }
        }
        return entityPosition;
    }

    /**
     * Finds the position of the enemy flag relative to the specified bot.
     *
     * @param bot The bot for which to find the enemy flag position.
     * @return The position of the enemy flag, or -1 if not found.
     */
    private int findEnemyPosition(Bot bot) {
        Player currentPlayer = null;
        int enemyPosition = -1;
        Iterator<Player> iterator = players.iterator();

        while (iterator.hasNext()) {
            currentPlayer = iterator.next();
            if (!currentPlayer.getBots().contains(bot)) {
                enemyPosition = findEntity(currentPlayer.getFlag());

                return enemyPosition;
            }
        }
        return enemyPosition;
    }

    /**
     * Finds the player associated with the specified bot.
     *
     * @param bot The bot for which to find the player.
     * @return The player associated with the bot, or null if not found.
     */
    private Player findPlayer(Bot bot) {
        Player player = null;
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player currentPlayer = iterator.next();
            if (currentPlayer.getBots().contains(bot)) {
                player = currentPlayer;
                return player;
            }
        }

        return player;
    }

    /**
     * Retrieves the players of the game.
     *
     * @return The players of the game.
     */
    public Iterator<Player> getPlayers() {
        return players.iterator();
    }
}
