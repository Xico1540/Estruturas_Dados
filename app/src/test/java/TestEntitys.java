import algorithms.*;
import entitys.*;
import implementation_classes.ArrayUnorderedList;
import org.junit.jupiter.api.*;

public class TestEntitys {

    @Test
    public void Flag() {
        Flag flag = new Flag("Flag 1");
        Assertions.assertEquals("Flag 1", flag.getName());
    }

    @Test
    public void Bot() {
        ShortestPathBFS shortestPathBFS = new ShortestPathBFS();
        BelmanFord belmanFord = new BelmanFord();
        ShortestPathWeight shortestPathWeight = new ShortestPathWeight();

        //test getName
        Bot bot1 = new Bot("Bot SP", belmanFord);
        Assertions.assertEquals("Bot SP", bot1.getName());

        Bot bot2 = new Bot("Bot SP_BFS", shortestPathBFS);
        Bot bot3 = new Bot("Bot SP_BFS", shortestPathBFS);

        //test skipRound
        bot1.skipRound();
        bot2.skipRound();
        Assertions.assertEquals(2, bot3.getSkipRoundCount());
    }

    @Test
    public void Position() {

        Position position = new Position(null);

        Bot bot = new Bot("Bot SP", null);
        Assertions.assertEquals("X", position.toString());

        position.setEntity(bot);
        Assertions.assertEquals("Bot SP", position.toString());
        Assertions.assertEquals(bot, position.getEntity());

    }

    @Test
    public void Player() {


        BelmanFord belmanFord = new BelmanFord();

        // create bots
        Bot bot1 = new Bot("Bot SP", belmanFord);

        //create Flag
        Flag flag1 = new Flag("Flag 1");

        // create list of bots for player1
        ArrayUnorderedList<Bot> bots1 = new ArrayUnorderedList<>();
        bots1.addToRear(bot1);

        Player player = new Player("Player 1", bots1, flag1);

        Assertions.assertEquals("Player 1", player.getName());

        Assertions.assertEquals(bots1, player.getBots());

    }
}
