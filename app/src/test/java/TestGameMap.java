import entitys.Bot;
import entitys.Flag;
import entitys.Player;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidVerticeNumberException;
import gameManagement.GameMap;
import implementation_classes.ArrayUnorderedList;
import implementation_classes.Map;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestGameMap {

    @Test
    public void gameMap() throws InvalidVerticeNumberException, EmptyCollectionException, ElementNotFoundException {
        Map map = new Map();
        map.directedMap(20, 0.30);

        Bot bot1 = new Bot("Bot SP", null);
        Bot bot2 = new Bot("Bot SP_BFS", null);

        ArrayUnorderedList<Bot> bots1 = new ArrayUnorderedList<>();
        bots1.addToRear(bot1);
        ArrayUnorderedList<Bot> bots2 = new ArrayUnorderedList<>();
        bots2.addToRear(bot2);

        //no player 1 flag
        Player player1 = new Player("Player 1", bots1, null);
        Player player2 = new Player("Player 2", bots2, null);
        ArrayUnorderedList<Player> players = new ArrayUnorderedList<>();
        try {
            GameMap gameMap = new GameMap(map, players,0,7);
        }catch (Exception exception){
            Assertions.assertEquals("There must be a flag for the first player", exception.getMessage());
        }

        //no player 2 flag
        Flag flag1 = new Flag("Flag 1");
        player1.setFlag(flag1);
        players = new ArrayUnorderedList<>();
        players.addToRear(player1);
        players.addToRear(player2);
        try {
            GameMap gameMap = new GameMap(map, players,0,7);
        }catch (Exception exception){
            Assertions.assertEquals("There must be a flag for the second player", exception.getMessage());
        }

        //non-existing bot
        GameMap gameMap = new GameMap(map, players,0,7);
        Bot noBot = new Bot("No Bot", null);
        Exception exception = assertThrows(ElementNotFoundException.class, () -> gameMap.round(noBot));
        Assertions.assertEquals("This bot is not in the game", exception.getMessage());







    }

}
