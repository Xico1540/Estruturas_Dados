package Interface;

import entitys.Bot;
import entitys.Entity;
import entitys.Player;
import entitys.Position;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import gameManagement.GameMap;
import implementation_classes.Map;

import java.util.Iterator;

public class RoundMenu {

    private final int DEFAULT_PRINT_SIZE = 15;

    /**
     * Represents a round in the game where a bot takes actions based on the game map.
     *
     * @param bot      The bot participating in the round.
     * @param gameMap  The game map on which the round is taking place.
     * @throws EmptyCollectionException    If attempting to access an element from an empty collection.
     * @throws ElementNotFoundException    If an expected element is not found in the collection.
     */
    public void round(Bot bot, GameMap gameMap) throws EmptyCollectionException, ElementNotFoundException {
        // Find the player

   int round = gameMap.round(bot);
   int nextMove = findBotPosition(bot, gameMap.getMap());
        switch (round) {
            case 1:
                winner(bot,gameMap.getPlayers());
                break;
            case 0:
                obstacle(bot,nextMove);
                break;
            case -1:
                move(bot,nextMove);
                break;
        }
    }

    /**
     * Finds the position of an entity on the map.
     *
     * @param entity The entity for which to find the position.
     * @param map    The map on which to search for the entity.
     * @return The position of the entity on the map, or -1 if not found.
     */
    private int findBotPosition(Entity entity, Map map) {
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
     * Finds the player associated with a bot from the list of players.
     *
     * @param bot     The bot for which to find the player.
     * @param players The iterator over the list of players.
     * @return The player associated with the bot, or null if not found.
     */
    private Player findPlayer(Bot bot, Iterator<Player> players) {
        Player player = null;

        while (players.hasNext()) {
            Player currentPlayer = players.next();
            if (currentPlayer.getBots().contains(bot)) {
                player = currentPlayer;
                return player;
            }
        }

        return player;
    }


    /**
     * Handles the case when a bot captures the enemy flag and becomes the winner.
     *
     * @param bot     The winning bot.
     * @param players The iterator over the list of players.
     */
    private void winner(Bot bot, Iterator<Player> players){
        Player player = findPlayer(bot, players);
        System.out.println(" 🚩 " + bot.getName() + " captured the enemy flag! 🚩");
        System.out.println("\n" + "-".repeat(DEFAULT_PRINT_SIZE) + " 🎉 Congratulations! 🎉 " + "-".repeat(DEFAULT_PRINT_SIZE) + "\n");
        System.out.println("🌟 A moment of glory! " + player.getName() + " is the champion with the big W! 🌟 ");
        System.out.println("😔 Tough break for the others. Keep your heads up! 😔 ");

        System.exit(0);
    }

    /**
     * Handles the case when a bot encounters an obstacle during the round.
     *
     * @param bot       The bot encountering the obstacle.
     * @param nextMove  The position of the obstacle.
     */
    private void obstacle(Bot bot,int nextMove){
        System.out.println("🚧 Uh-oh! " + bot.getName() + " encountered an obstacle at position " + nextMove +
                ". Skipping this round! 🚧");
        bot.skipRound();

        // Check if max skip rounds reached
        if (bot.getSkipRoundCount() == bot.getBotCounter()) {
            System.out.println("\n" + "-".repeat(DEFAULT_PRINT_SIZE) + " ⌛ It's a Draw! ⌛ " + "-".repeat(DEFAULT_PRINT_SIZE) + "\n");
            System.out.println("🎮 No winners this time. Game over! 🎮");
            System.exit(0);
        }
    }
    /**
     * Handles the case when a bot advances to a new position during the round.
     *
     * @param bot       The bot advancing.
     * @param nextMove  The new position of the bot.
     */
    private void move(Bot bot,int nextMove){
        System.out.println("🚀 " + bot.getName() + " advanced to position #" + nextMove + ". Getting closer!!  🚀");
    }



}
