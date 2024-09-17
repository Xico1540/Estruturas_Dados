package algorithms;

import entitys.Position;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import implementation_classes.Map;

public interface Algorithm {

    /**
     * Determines the next move for the bot based on its behavior, considering the given map and vertex positions.
     *
     * @param map The map representing the environment or game board.
     * @param firstVertex The position of the first vertex relevant to the bot's decision-making.
     * @param secondVertex The position of the second vertex relevant to the bot's decision-making.
     * @return An integer representing the next move for the bot. The specific interpretation of this value
     *         depends on the bot's behavior or the context of the application.
     * @throws EmptyCollectionException If the map is empty, and no information is available for decision-making.
     * @throws ElementNotFoundException If one or both of the specified vertex positions are not found in the map.
     */
    public int nextMove(Map map, Position firstVertex, Position secondVertex) throws EmptyCollectionException, ElementNotFoundException;

}
