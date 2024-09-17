/**
 * Represents a position on the game board with an associated entity.
 */
package entitys;

public class Position {
    Entity entity;

    /**
     * Creates a new position with the specified entity.
     *
     * @param entity The entity associated with the position.
     */
    public Position(Entity entity) {
        this.entity = entity;
    }

    /**
     * Retrieves the entity associated with the position.
     *
     * @return The entity associated with the position.
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets the entity associated with the position.
     *
     * @param entity The new entity to be associated with the position.
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Returns a string representation of the position.
     * If the entity is null, returns "X". Otherwise, returns the name of the entity.
     *
     * @return A string representation of the position.
     */
    @Override
    public String toString() {
        if (entity == null)
            return "X";
        return entity.getName();
    }
}
