/**
 * Represents a player entity with a name, an array of bots, and a flag.
 * Extends the Entity class.
 */
package entitys;

import implementation_classes.ArrayUnorderedList;

public class Player extends Entity {

    private ArrayUnorderedList<Bot> bots;     // Array of bots
    private Flag flag;      // Flag of the player

    /**
     * Creates a new player with the specified name, array of bots, and flag.
     *
     * @param name The name of the player.
     * @param bots An array of bots associated with the player.
     * @param flag The flag owned by the player.
     */
    public Player(String name, ArrayUnorderedList<Bot> bots, Flag flag) {
        super(name);
        this.bots = bots;
        this.flag = flag;
    }

    /**
     * Retrieves the array of bots associated with the player.
     *
     * @return The array of bots associated with the player.
     */
    public ArrayUnorderedList<Bot> getBots() {
        return bots;
    }

    /**
     * Retrieves the flag owned by the player.
     *
     * @return The flag owned by the player.
     */
    public Flag getFlag() {
        return flag;
    }

    /**
     * Sets the flag owned by the player.
     *
     * @param flag The new flag owned by the player.
     */
    public void setFlag(Flag flag) {
        this.flag = flag;
    }
}
