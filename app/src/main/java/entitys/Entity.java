/**
 * Represents an abstract entity with a name.
 */
package entitys;

public abstract class Entity {

    String name;

    /**
     * Creates a new entity with the specified name.
     *
     * @param name The name of the entity.
     */
    public Entity(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the entity.
     *
     * @return The name of the entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the entity.
     *
     * @param name The new name for the entity.
     */
    public void setName(String name) {
        this.name = name;
    }
}
