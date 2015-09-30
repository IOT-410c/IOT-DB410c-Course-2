package net.calit2.mooc.iot_db410c.calit2bird.gameprocessor;

/**
 * Name:        Obstacle
 * Description: Defines a concrete Actor class that represents an
 *              Obstacle in the game
 */
public class Obstacle extends Actor{

    /**
     * Name:        Obstacle
     * Description: Constructor for the class. Instantiates the coordinate
     *              coordinates of the Actor with the passed in parameters.
     *
     * @param       x - X coordinate for the Actor
     * @param       y - Y coordinate for the Actor
     */
    public Obstacle(int x, int y) {
        super(x, y);
    }

    /**
     * Name:        Obstacle
     * Description: Constructor for the class. Instantiates an actor
     *              using the passed in location
     *
     * @param       location - Location for the Actor
     */
    public Obstacle(Location location) {
        super(location);
    }

    /**
     * Name:        move
     * Description: Shifts the obstacle to the left
     *
     * @return      Boolean indicating if the Obstacle was successfully moved
     */
    @Override
    public boolean move() {
        return decrementX();
    }
}
