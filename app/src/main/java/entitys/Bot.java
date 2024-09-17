/**
 * Represents a bot entity with specific behavior on a game map.
 * Extends the Entity class.
 */
package entitys;

import algorithms.Algorithm;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import implementation_classes.Map;

public class Bot extends Entity {

    private Algorithm behavior;   // Behavior of the bot

    private static int skipRoundCount = 0;  // Number of rounds to skip

    private static int counter;

    /**
     * Creates a new Bot with the specified name and behavior.
     *
     * @param name The name of the bot.
     * @param behavior The behavior algorithm defining the bot's decision-making process.
     */
    public Bot(String name, Algorithm behavior) {
        super(name);
        this.behavior = behavior;
        counter++;
    }

    /**
     * Determines the next move for the bot based on its behavior, considering the given map and target position.
     *
     * @param map The map representing the environment or game board.
     * @param current The current position of the bot on the map.
     * @param target The target position relevant to the bot's decision-making.
     * @return An integer representing the next move for the bot. The specific interpretation of this value
     *         depends on the bot's behavior or the context of the application.
     * @throws EmptyCollectionException If the map is empty, and no information is available for decision-making.
     * @throws ElementNotFoundException If the specified current or target position is not found in the map.
     */
    public int nextMove(Map map, Position current, Position target) throws EmptyCollectionException, ElementNotFoundException {
        return behavior.nextMove(map, current, target);
    }

    /**
     * Retrieves the current count of rounds to skip.
     *
     * @return The current count of rounds to skip.
     */
    public static int getSkipRoundCount() {
        return skipRoundCount;
    }

    /**
     * Resets the count of rounds to skip back to zero.
     */
    public static void resetSkipRound() {
        Bot.skipRoundCount = 0;
    }

    /**
     * Retrieves the current counter of bots.
     *
     * @return The current counter of bots.
     */
    public static int getBotCounter() {
        return counter;
    }



    /**
     * Increments the count of rounds to skip.
     */
    public static void skipRound() {
        Bot.skipRoundCount++;
    }
}
